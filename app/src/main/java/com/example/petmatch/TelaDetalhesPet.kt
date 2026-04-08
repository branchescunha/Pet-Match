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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    onVoltar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Branco),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = pet.imagem),
                        contentDescription = pet.nome,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
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
                InfoLinha("Saúde", pet.saude)
                InfoLinha("Localização", pet.localizacao)
                InfoLinha("Tutor", pet.tutor)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (perfilUsuario == "Cliente") {
            BotaoPadrao(
                texto = if (estaFavoritado) "Remover dos favoritos" else "Adicionar aos favoritos",
                onClick = { onFavoritarClick(pet) }
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