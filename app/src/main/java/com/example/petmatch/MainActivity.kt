package com.example.petmatch

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petmatch.ui.theme.PetMatchTheme

class MainActivity : ComponentActivity() {

    private val solicitarPermissaoNotificacao =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHelper.criarCanal(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            solicitarPermissaoNotificacao.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        enableEdgeToEdge()

        setContent {
            PetMatchTheme {
                val navController = rememberNavController()

                var nomeUsuario by rememberSaveable { mutableStateOf("") }
                var perfilUsuario by rememberSaveable { mutableStateOf("") }
                var usuarioLogado by rememberSaveable { mutableStateOf(false) }

                var contaNome by rememberSaveable { mutableStateOf("") }
                var contaEmail by rememberSaveable { mutableStateOf("") }
                var contaSenha by rememberSaveable { mutableStateOf("") }
                var mensagemErroLogin by rememberSaveable { mutableStateOf("") }

                val pets = remember {
                    mutableStateListOf<Pet>().apply {
                        addAll(DadosPets.lista)
                    }
                }

                val favoritos = remember {
                    mutableStateListOf<Pet>()
                }

                val locaisAjuda = remember {
                    mutableStateListOf(
                        LocalAjuda(
                            nome = "Abrigo Amor Animal",
                            necessidade = "Ração, areia e medicamentos",
                            pix = "amoranimal@pix.com",
                            localizacao = "Águas Claras - DF"
                        ),
                        LocalAjuda(
                            nome = "Lar Temporário Patinhas",
                            necessidade = "Cobertas, ração e produtos de limpeza",
                            pix = "patinhas@pix.com",
                            localizacao = "Taguatinga - DF"
                        ),
                        LocalAjuda(
                            nome = "ONG Acolher Pets",
                            necessidade = "Ajuda financeira e doações",
                            pix = "acolherpets@pix.com",
                            localizacao = "Brasília - DF"
                        )
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        val mostrarBottomBar =
                            currentRoute == "lista" ||
                                    currentRoute == "cadastroPet" ||
                                    currentRoute == "ajudePet" ||
                                    currentRoute == "perfilUsuario"

                        if (mostrarBottomBar) {
                            BottomNavigationBar(
                                currentRoute = currentRoute,
                                perfilUsuario = perfilUsuario,
                                usuarioLogado = usuarioLogado,
                                onNavigate = { rota ->
                                    if (!usuarioLogado && (rota == "perfilUsuario" || rota == "cadastroPet")) {
                                        navController.navigate("login")
                                        return@BottomNavigationBar
                                    }

                                    if (currentRoute != rota) {
                                        navController.navigate(rota) {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "boasVindas",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("boasVindas") {
                            TelaBoasVindas(
                                onEntrarClick = {
                                    mensagemErroLogin = ""
                                    navController.navigate("login")
                                },
                                onExplorarClick = {
                                    nomeUsuario = "Visitante"
                                    perfilUsuario = "Visitante"
                                    usuarioLogado = false

                                    navController.navigate("lista") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        composable("login") {
                            TelaLogin(
                                mensagemErro = mensagemErroLogin,
                                onEntrarClick = { email, senha ->
                                    when {
                                        contaEmail.isBlank() -> {
                                            mensagemErroLogin =
                                                "Nenhuma conta cadastrada. Crie uma conta primeiro."
                                        }

                                        email != contaEmail || senha != contaSenha -> {
                                            mensagemErroLogin = "E-mail ou senha incorretos."
                                        }

                                        else -> {
                                            nomeUsuario = contaNome
                                            usuarioLogado = true
                                            mensagemErroLogin = ""

                                            NotificationHelper.mostrarNotificacao(
                                                context = this@MainActivity,
                                                titulo = "Login realizado",
                                                mensagem = "Bem-vindo ao PetMatch, $contaNome.",
                                                id = 4
                                            )

                                            navController.navigate("perfil") {
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                },
                                onCriarContaClick = {
                                    mensagemErroLogin = ""
                                    navController.navigate("cadastroUsuario")
                                }
                            )
                        }

                        composable("cadastroUsuario") {
                            TelaCadastroUsuario(
                                onCriarContaClick = { nome, email, senha ->
                                    contaNome = nome
                                    contaEmail = email
                                    contaSenha = senha
                                    mensagemErroLogin =
                                        "Conta criada com sucesso. Faça login para continuar."

                                    NotificationHelper.mostrarNotificacao(
                                        context = this@MainActivity,
                                        titulo = "Conta criada",
                                        mensagem = "Sua conta PetMatch foi criada com sucesso.",
                                        id = 5
                                    )

                                    navController.navigate("login") {
                                        popUpTo("cadastroUsuario") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                onVoltarClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("perfil") {
                            TelaEscolhaPerfil(
                                nomeUsuario = nomeUsuario,
                                onContinuarClick = { perfil ->
                                    perfilUsuario = perfil
                                    usuarioLogado = true

                                    navController.navigate("lista") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        composable("lista") {
                            TelaListaPets(
                                nomeUsuario = nomeUsuario,
                                perfilUsuario = perfilUsuario,
                                pets = pets,
                                onDetalhesClick = { pet ->
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("petSelecionado", pet)

                                    navController.navigate("detalhes")
                                },
                                onEntrarClick = {
                                    mensagemErroLogin = ""
                                    navController.navigate("login")
                                }
                            )
                        }

                        composable("detalhes") {
                            val pet = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<Pet>("petSelecionado")

                            pet?.let {
                                TelaDetalhesPet(
                                    pet = it,
                                    perfilUsuario = perfilUsuario,
                                    estaFavoritado = favoritos.any { favorito ->
                                        favorito.nome == it.nome && favorito.tutor == it.tutor
                                    },
                                    onFavoritarClick = { petSelecionado ->
                                        if (!usuarioLogado || perfilUsuario == "Visitante") {
                                            mensagemErroLogin =
                                                "Entre na sua conta para favoritar pets."
                                            navController.navigate("login")
                                            return@TelaDetalhesPet
                                        }

                                        val jaExiste = favoritos.any { favorito ->
                                            favorito.nome == petSelecionado.nome &&
                                                    favorito.tutor == petSelecionado.tutor
                                        }

                                        if (jaExiste) {
                                            favoritos.removeAll { favorito ->
                                                favorito.nome == petSelecionado.nome &&
                                                        favorito.tutor == petSelecionado.tutor
                                            }

                                            NotificationHelper.mostrarNotificacao(
                                                context = this@MainActivity,
                                                titulo = "Favorito removido",
                                                mensagem = "${petSelecionado.nome} foi removido dos favoritos.",
                                                id = 2
                                            )
                                        } else {
                                            favoritos.add(petSelecionado)

                                            NotificationHelper.mostrarNotificacao(
                                                context = this@MainActivity,
                                                titulo = "Pet favoritado",
                                                mensagem = "${petSelecionado.nome} foi adicionado aos seus favoritos.",
                                                id = 1
                                            )
                                        }
                                    },
                                    onAbrirMapaClick = { petSelecionado ->
                                        NotificationHelper.mostrarNotificacao(
                                            context = this@MainActivity,
                                            titulo = "Localização consultada",
                                            mensagem = "Abrindo a localização aproximada de ${petSelecionado.nome}.",
                                            id = 6
                                        )

                                        navController.currentBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("petSelecionado", petSelecionado)

                                        navController.navigate("mapaPet")
                                    },
                                    onVoltar = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        composable("mapaPet") {
                            val pet = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<Pet>("petSelecionado")

                            pet?.let {
                                TelaMapaPet(
                                    pet = it,
                                    onVoltar = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        composable("cadastroPet") {
                            if (!usuarioLogado || perfilUsuario != "Tutor") {
                                mensagemErroLogin = "Entre como tutor para cadastrar pets."
                                navController.navigate("login")
                            } else {
                                TelaCadastroPet(
                                    nomeTutor = nomeUsuario,
                                    onSalvarPet = { novoPet ->
                                        pets.add(0, novoPet)

                                        NotificationHelper.mostrarNotificacao(
                                            context = this@MainActivity,
                                            titulo = "Pet cadastrado",
                                            mensagem = "${novoPet.nome} foi adicionado à lista de adoção.",
                                            id = 3
                                        )

                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        composable("ajudePet") {
                            TelaAjudePet(
                                perfilUsuario = perfilUsuario,
                                locais = locaisAjuda,
                                onCadastrarLocal = { novoLocal ->
                                    locaisAjuda.add(0, novoLocal)
                                }
                            )
                        }

                        composable("perfilUsuario") {
                            if (!usuarioLogado || perfilUsuario == "Visitante") {
                                mensagemErroLogin = "Entre na sua conta para acessar o perfil."
                                navController.navigate("login")
                            } else {
                                TelaPerfilUsuario(
                                    nomeUsuario = nomeUsuario,
                                    perfilUsuario = perfilUsuario,
                                    favoritos = favoritos,
                                    pets = pets,
                                    onDetalhesClick = { pet ->
                                        navController.currentBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("petSelecionado", pet)

                                        navController.navigate("detalhes")
                                    },
                                    onSairClick = {
                                        nomeUsuario = ""
                                        perfilUsuario = ""
                                        usuarioLogado = false
                                        favoritos.clear()

                                        navController.navigate("boasVindas") {
                                            popUpTo("boasVindas") {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    perfilUsuario: String,
    usuarioLogado: Boolean,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "lista",
            onClick = { onNavigate("lista") },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Início")
            },
            label = {
                Text("Início")
            }
        )

        if (usuarioLogado && perfilUsuario == "Tutor") {
            NavigationBarItem(
                selected = currentRoute == "cadastroPet",
                onClick = { onNavigate("cadastroPet") },
                icon = {
                    Icon(Icons.Default.AddCircle, contentDescription = "Cadastrar")
                },
                label = {
                    Text("Cadastrar")
                }
            )
        }

        NavigationBarItem(
            selected = currentRoute == "ajudePet",
            onClick = { onNavigate("ajudePet") },
            icon = {
                Icon(Icons.Default.Favorite, contentDescription = "Ajuda")
            },
            label = {
                Text("Ajuda")
            }
        )

        if (usuarioLogado) {
            NavigationBarItem(
                selected = currentRoute == "perfilUsuario",
                onClick = { onNavigate("perfilUsuario") },
                icon = {
                    Icon(Icons.Default.Person, contentDescription = "Perfil")
                },
                label = {
                    Text("Perfil")
                }
            )
        }
    }
}