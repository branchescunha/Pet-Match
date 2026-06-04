package com.example.petmatch

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.BordaSuave
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.CinzaCampo
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

data class LocalAjuda(
    val nome: String,
    val necessidade: String,
    val pix: String,
    val localizacao: String
)

@Composable
fun TelaAjudePet(
    perfilUsuario: String,
    locais: List<LocalAjuda>,
    onCadastrarLocal: (LocalAjuda) -> Unit
) {
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var necessidade by remember { mutableStateOf("") }
    var pix by remember { mutableStateOf("") }
    var localizacao by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Ajude um Pet",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Instituições que precisam de apoio neste momento.",
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${locais.size} instituições cadastradas",
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrimario
            )
        }

        if (perfilUsuario == "Tutor") {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Branco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Cadastrar instituição",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextoPrimario
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Adicione abrigos, lares temporários ou ONGs que precisam de apoio.",
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CampoAjuda("Nome da instituição", nome) {
                            nome = it
                            erro = ""
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        CampoAjuda("Necessidade", necessidade) {
                            necessidade = it
                            erro = ""
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        CampoAjuda("PIX", pix) {
                            pix = it
                            erro = ""
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        CampoAjuda("Localização", localizacao) {
                            localizacao = it
                            erro = ""
                        }

                        if (erro.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = erro,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        BotaoPadrao(
                            texto = "Cadastrar instituição",
                            onClick = {
                                erro = when {
                                    nome.isBlank() -> "Informe o nome da instituição."
                                    necessidade.isBlank() -> "Informe a necessidade."
                                    pix.isBlank() -> "Informe o PIX."
                                    localizacao.isBlank() -> "Informe a localização."
                                    else -> ""
                                }

                                if (erro.isBlank()) {
                                    val novoLocal = LocalAjuda(
                                        nome = nome,
                                        necessidade = necessidade,
                                        pix = pix,
                                        localizacao = localizacao
                                    )

                                    onCadastrarLocal(novoLocal)

                                    NotificationHelper.mostrarNotificacao(
                                        context = context,
                                        titulo = "Instituição cadastrada",
                                        mensagem = "$nome foi adicionada à lista de apoio.",
                                        id = 7
                                    )

                                    nome = ""
                                    necessidade = ""
                                    pix = ""
                                    localizacao = ""
                                }
                            }
                        )
                    }
                }
            }
        }

        items(locais) { local ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Branco),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = local.nome,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoPrimario
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Necessidade:", color = TextoPrimario)
                    Text(local.necessidade, color = TextoSecundario)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("PIX:", color = TextoPrimario)
                    Text(local.pix, color = TextoSecundario)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Localização:", color = TextoPrimario)
                    Text(local.localizacao, color = TextoSecundario)

                    Spacer(modifier = Modifier.height(16.dp))

                    BotaoPadrao(
                        texto = "Quero ajudar",
                        onClick = {
                            NotificationHelper.mostrarNotificacao(
                                context = context,
                                titulo = "Ajuda registrada",
                                mensagem = "Obrigado por apoiar ${local.nome}.",
                                id = 100
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CampoAjuda(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CinzaCampo,
            unfocusedContainerColor = CinzaCampo,
            focusedIndicatorColor = BordaSuave,
            unfocusedIndicatorColor = BordaSuave
        )
    )
}