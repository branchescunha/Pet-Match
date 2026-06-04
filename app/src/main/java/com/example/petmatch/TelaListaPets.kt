package com.example.petmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onDetalhesClick: (Pet) -> Unit,
    onEntrarClick: () -> Unit
) {
    var pesquisa by remember { mutableStateOf("") }
    var filtro by remember { mutableStateOf("Todos") }

    val petsFiltrados = pets.filter { pet ->
        val correspondePesquisa = pet.nome.contains(pesquisa, ignoreCase = true)

        val correspondeFiltro = when (filtro) {
            "Gatos" -> pet.tipo == "Gato"
            "Cachorros" -> pet.tipo == "Cachorro"
            else -> true
        }

        correspondePesquisa && correspondeFiltro
    }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
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
                    }

                    if (perfilUsuario == "Visitante") {
                        Text(
                            text = "Entrar",
                            modifier = Modifier
                                .background(
                                    color = AmareloPrincipal,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable { onEntrarClick() }
                                .padding(horizontal = 18.dp, vertical = 10.dp),
                            color = TextoPrimario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Pets disponíveis para adoção",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardDashboard(
                        titulo = "\uD83D\uDC3E Pets",
                        valor = pets.size.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    CardDashboard(
                        titulo = "\uD83D\uDC31 Gatos",
                        valor = pets.count { it.tipo == "Gato" }.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    CardDashboard(
                        titulo = "\uD83D\uDC36 Cães",
                        valor = pets.count { it.tipo == "Cachorro" }.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Buscar pet pelo nome") },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = filtro == "Todos",
                        onClick = { filtro = "Todos" },
                        label = { Text("Todos") }
                    )

                    FilterChip(
                        selected = filtro == "Gatos",
                        onClick = { filtro = "Gatos" },
                        label = { Text("Gatos") }
                    )

                    FilterChip(
                        selected = filtro == "Cachorros",
                        onClick = { filtro = "Cachorros" },
                        label = { Text("Cachorros") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${petsFiltrados.size} pets encontrados",
                    color = TextoSecundario
                )
            }
        }

        items(petsFiltrados) { pet ->
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
                        contentScale = ContentScale.Fit
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

@Composable
private fun CardDashboard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Branco)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall,
                color = TextoPrimario
            )

            Text(
                text = titulo,
                color = TextoSecundario
            )
        }
    }
}