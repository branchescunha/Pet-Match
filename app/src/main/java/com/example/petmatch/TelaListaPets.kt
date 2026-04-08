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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.AmareloPrincipal
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaListaPets(
    nomeUsuario: String,
    perfilUsuario: String,
    pets: List<Pet>,
    onDetalhesClick: (Pet) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AmareloSuave)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Olá, $nomeUsuario 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextoPrimario
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Perfil: $perfilUsuario",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Pets disponíveis para adoção",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )
            }
        }

        items(pets) { pet ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Branco),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { onDetalhesClick(pet) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = pet.imagem),
                        contentDescription = pet.nome,
                        modifier = Modifier.size(88.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
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

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Ver detalhes",
                            modifier = Modifier
                                .background(
                                    color = AmareloPrincipal,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            color = TextoPrimario
                        )
                    }
                }
            }
        }
    }
}