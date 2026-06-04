package com.example.petmatch

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaMapaPet(
    pet: Pet,
    onVoltar: () -> Unit
) {
    val context = LocalContext.current

    var carregando by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<LocalizacaoResponse?>(null) }

    LaunchedEffect(pet.localizacao) {
        try {
            val resposta = RetrofitClient.localizacaoApi.buscarLocalizacao(
                localizacao = "${pet.localizacao}, Brasil"
            )

            resultado = resposta.firstOrNull()

            if (resultado == null) {
                erro = "Não foi possível encontrar essa localização."
            }
        } catch (e: Exception) {
            erro = "Erro ao buscar localização. Verifique a conexão com a internet."
        } finally {
            carregando = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Localização do Pet",
            style = MaterialTheme.typography.headlineMedium,
            color = TextoPrimario
        )

        Text(
            text = pet.nome,
            style = MaterialTheme.typography.titleLarge,
            color = TextoPrimario
        )

        Text(
            text = pet.localizacao,
            color = TextoSecundario
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Branco),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                when {
                    carregando -> {
                        Text(
                            text = "Buscando localização pela API...",
                            color = TextoSecundario
                        )
                    }

                    erro.isNotBlank() -> {
                        Text(
                            text = erro,
                            color = TextoSecundario
                        )
                    }

                    resultado != null -> {
                        Text(
                            text = "Endereço encontrado",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoPrimario
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = resultado!!.display_name,
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Latitude: ${resultado!!.lat}",
                            color = TextoSecundario
                        )

                        Text(
                            text = "Longitude: ${resultado!!.lon}",
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        BotaoPadrao(
                            texto = "Abrir no Maps",
                            onClick = {
                                val uri = Uri.parse(
                                    "geo:${resultado!!.lat},${resultado!!.lon}?q=${resultado!!.lat},${resultado!!.lon}(${pet.nome})"
                                )

                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }

        BotaoSecundario(
            texto = "Voltar",
            onClick = onVoltar
        )
    }
}