package com.example.petmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PetMatchTheme {
                val navController = rememberNavController()

                var nomeUsuario by rememberSaveable { mutableStateOf("") }
                var perfilUsuario by rememberSaveable { mutableStateOf("") }

                val pets = remember {
                    mutableStateListOf<Pet>().apply {
                        addAll(DadosPets.lista)
                    }
                }

                val favoritos = remember {
                    mutableStateListOf<Pet>()
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
                                onNavigate = { rota ->
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
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("login") {
                            TelaLogin(
                                onEntrarClick = { nome ->
                                    nomeUsuario = nome
                                    navController.navigate("perfil")
                                }
                            )
                        }

                        composable("perfil") {
                            TelaEscolhaPerfil(
                                nomeUsuario = nomeUsuario,
                                onContinuarClick = { perfil ->
                                    perfilUsuario = perfil
                                    navController.navigate("lista")
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
                                        val jaExiste = favoritos.any { favorito ->
                                            favorito.nome == petSelecionado.nome &&
                                                    favorito.tutor == petSelecionado.tutor
                                        }

                                        if (jaExiste) {
                                            favoritos.removeAll { favorito ->
                                                favorito.nome == petSelecionado.nome &&
                                                        favorito.tutor == petSelecionado.tutor
                                            }
                                        } else {
                                            favoritos.add(petSelecionado)
                                        }
                                    },
                                    onVoltar = { navController.popBackStack() }
                                )
                            }
                        }

                        composable("cadastroPet") {
                            TelaCadastroPet(
                                nomeTutor = nomeUsuario,
                                onSalvarPet = { novoPet ->
                                    pets.add(0, novoPet)
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("ajudePet") {
                            TelaAjudePet()
                        }

                        composable("perfilUsuario") {
                            TelaPerfilUsuario(
                                nomeUsuario = nomeUsuario,
                                perfilUsuario = perfilUsuario,
                                favoritos = favoritos,
                                onDetalhesClick = { pet ->
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("petSelecionado", pet)

                                    navController.navigate("detalhes")
                                },
                                onSairClick = {
                                    nomeUsuario = ""
                                    perfilUsuario = ""
                                    favoritos.clear()

                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
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

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    perfilUsuario: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "lista",
            onClick = { onNavigate("lista") },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Início")
            },
            label = { Text("Início") }
        )

        if (perfilUsuario == "Tutor") {
            NavigationBarItem(
                selected = currentRoute == "cadastroPet",
                onClick = { onNavigate("cadastroPet") },
                icon = {
                    Icon(Icons.Default.AddCircle, contentDescription = "Cadastrar")
                },
                label = { Text("Cadastrar") }
            )
        }

        NavigationBarItem(
            selected = currentRoute == "ajudePet",
            onClick = { onNavigate("ajudePet") },
            icon = {
                Icon(Icons.Default.Favorite, contentDescription = "Ajuda")
            },
            label = { Text("Ajuda") }
        )

        NavigationBarItem(
            selected = currentRoute == "perfilUsuario",
            onClick = { onNavigate("perfilUsuario") },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Perfil")
            },
            label = { Text("Perfil") }
        )
    }
}