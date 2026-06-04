package com.example.petmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaDetalhesPet(
    pet: Pet,
    perfilUsuario: String,
    estaFavoritado: Boolean,
    onFavoritarClick: (Pet) -> Unit,
    onAbrirMapaClick: (Pet) -> Unit,
    onVoltar: () -> Unit
) {
    var mostrarDialogLogin by remember { mutableStateOf(false) }

    if (mostrarDialogLogin) {
        AlertDialog(
            onDismissRequest = { mostrarDialogLogin = false },
            title = { Text("Entre para continuar") },
            text = { Text("Para favoritar este pet, você precisa entrar ou criar uma conta no PetMatch.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogLogin = false
                        onFavoritarClick(pet)
                    }
                ) {
                    Text("Entrar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDialogLogin = false }
                ) {
                    Text("Agora não")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Branco),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = pet.imagem),
                        contentDescription = pet.nome,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Column {
                        Text(
                            text = pet.nome,
                            style = MaterialTheme.typography.headlineSmall,
                            color = TextoPrimario
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${pet.tipo} • ${pet.porte}",
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        FilterChip(
                            selected = true,
                            onClick = { },
                            label = { Text("Disponível para adoção") },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = AmareloSuave
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Descrição",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoPrimario
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = pet.descricao,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(20.dp))

                InfoLinha("Idade", "${pet.idade} anos")
                InfoLinha("Sexo", pet.sexo)
                InfoLinha("Peso", "${pet.peso} kg")
                InfoLinha("Porte", pet.porte)

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AmareloSuave),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Condição de saúde",
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = pet.saude,
                            color = TextoPrimario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Localização",
                            color = TextoSecundario
                        )

                        Text(
                            text = pet.localizacao,
                            color = TextoPrimario
                        )
                    }

                    BotaoMapaPequeno(
                        onClick = { onAbrirMapaClick(pet) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AmareloSuave),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tutor responsável",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = pet.tutor,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoPrimario
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (perfilUsuario == "Cliente" || perfilUsuario == "Visitante") {
            BotaoPadrao(
                texto = if (estaFavoritado) "Remover dos favoritos" else "Adicionar aos favoritos",
                onClick = {
                    if (perfilUsuario == "Visitante") {
                        mostrarDialogLogin = true
                    } else {
                        onFavoritarClick(pet)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        BotaoSecundario(
            texto = "Voltar",
            onClick = onVoltar
        )
    }
}

@Composable
private fun BotaoMapaPequeno(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp)
    ) {
        Text("Ver mapa")
    }
}

@Composable
private fun InfoLinha(
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titulo,
            color = TextoSecundario
        )

        Text(
            text = valor,
            color = TextoPrimario,
            modifier = Modifier.wrapContentWidth()
        )
    }
}