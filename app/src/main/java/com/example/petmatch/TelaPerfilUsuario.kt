package com.example.petmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaPerfilUsuario(
    nomeUsuario: String,
    perfilUsuario: String,
    favoritos: List<Pet>,
    onDetalhesClick: (Pet) -> Unit,
    onSairClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Meu Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = TextoPrimario
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Nome: $nomeUsuario",
            style = MaterialTheme.typography.titleLarge,
            color = TextoPrimario
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Perfil: $perfilUsuario",
            style = MaterialTheme.typography.bodyLarge,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (perfilUsuario == "Cliente") {
            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (favoritos.isEmpty()) {
                Text(
                    text = "Você ainda não favoritou nenhum pet.",
                    color = TextoSecundario
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoritos) { pet ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Branco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            onClick = { onDetalhesClick(pet) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Image(
                                    painter = painterResource(id = pet.imagem),
                                    contentDescription = pet.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(12.dp))

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
                            }
                        }
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        BotaoSecundario(
            texto = "Sair da conta",
            onClick = onSairClick
        )
    }
}