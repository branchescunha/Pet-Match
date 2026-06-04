package com.example.petmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.AmareloPrincipal
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaPerfilUsuario(
    nomeUsuario: String,
    perfilUsuario: String,
    favoritos: List<Pet>,
    pets: List<Pet>,
    onDetalhesClick: (Pet) -> Unit,
    onSairClick: () -> Unit
) {
    val meusPets = pets.filter { it.tutor == nomeUsuario }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Meu Perfil",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Branco),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(AmareloSuave),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🐾",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Column {
                        Text(
                            text = nomeUsuario,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextoPrimario
                        )

                        Text(
                            text = perfilUsuario,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }

        item {
            if (perfilUsuario == "Cliente") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CardResumoPerfil(
                        titulo = "Favoritos",
                        valor = favoritos.size.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    CardResumoPerfil(
                        titulo = "Interações",
                        valor = favoritos.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pets favoritados",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )

                if (favoritos.isEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Branco)
                    ) {
                        Text(
                            text = "Você ainda não favoritou nenhum pet.",
                            modifier = Modifier.padding(20.dp),
                            color = TextoSecundario
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CardResumoPerfil(
                        titulo = "Meus pets",
                        valor = meusPets.size.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    CardResumoPerfil(
                        titulo = "Ativos",
                        valor = meusPets.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = AmareloSuave)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Área do Tutor",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextoPrimario
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Cadastre pets, acompanhe animais disponíveis e gerencie informações importantes para adoção.",
                            color = TextoSecundario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Meus pets cadastrados",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )

                if (meusPets.isEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Branco)
                    ) {
                        Text(
                            text = "Você ainda não cadastrou nenhum pet.",
                            modifier = Modifier.padding(20.dp),
                            color = TextoSecundario
                        )
                    }
                }
            }
        }

        if (perfilUsuario == "Cliente") {
            items(favoritos) { pet ->
                CardPetPerfil(
                    pet = pet,
                    onClick = { onDetalhesClick(pet) }
                )
            }
        } else {
            items(meusPets) { pet ->
                CardPetPerfil(
                    pet = pet,
                    onClick = { onDetalhesClick(pet) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            BotaoSecundario(
                texto = "Sair da conta",
                onClick = onSairClick
            )
        }
    }
}

@Composable
private fun CardResumoPerfil(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Branco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Text(
                text = titulo,
                color = TextoSecundario
            )
        }
    }
}

@Composable
private fun CardPetPerfil(
    pet: Pet,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Branco),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = pet.imagem),
                contentDescription = pet.nome,
                modifier = Modifier.size(92.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = pet.nome,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${pet.tipo} • ${pet.porte}",
                    color = TextoSecundario
                )

                Text(
                    text = "${pet.idade} anos • ${pet.sexo}",
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ver detalhes",
                    color = AmareloPrincipal
                )
            }
        }
    }
}