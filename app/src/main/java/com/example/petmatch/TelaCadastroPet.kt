package com.example.petmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.petmatch.ui.theme.BordaSuave
import com.example.petmatch.ui.theme.CinzaCampo
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaCadastroPet(
    nomeTutor: String,
    onSalvarPet: (Pet) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Gato") }
    var sexo by remember { mutableStateOf("Fêmea") }
    var porte by remember { mutableStateOf("Pequeno") }
    var descricao by remember { mutableStateOf("") }
    var localizacao by remember { mutableStateOf("") }
    var vacinado by remember { mutableStateOf(false) }
    var castrado by remember { mutableStateOf(false) }
    var idade by remember { mutableFloatStateOf(1f) }
    var pesoTexto by remember { mutableStateOf("") }
    var imagemSelecionada by remember { mutableStateOf<Int?>(null) }
    var erro by remember { mutableStateOf("") }

    val imagensDisponiveis = if (tipo == "Gato") {
        listOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3)
    } else {
        listOf(R.drawable.dog1, R.drawable.dog2, R.drawable.dog3)
    }

    LaunchedEffect(tipo) {
        imagemSelecionada = imagensDisponiveis.firstOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cadastrar Pet",
            style = MaterialTheme.typography.headlineMedium,
            color = TextoPrimario
        )

        Text(
            text = "Adicione as principais informações do animal.",
            color = TextoSecundario
        )

        Text("Foto do pet", color = TextoPrimario)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            imagensDisponiveis.forEach { imagem ->
                Image(
                    painter = painterResource(id = imagem),
                    contentDescription = "Foto do pet",
                    modifier = Modifier
                        .size(80.dp)
                        .border(
                            width = if (imagemSelecionada == imagem) 3.dp else 1.dp,
                            color = if (imagemSelecionada == imagem) AmareloPrincipal else BordaSuave,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            imagemSelecionada = imagem
                            erro = ""
                        }
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        CampoForm("Nome do pet", nome) {
            nome = it
            erro = ""
        }

        Text("Tipo", color = TextoPrimario)
        SeletorDuplo("Gato", "Cachorro", tipo) {
            tipo = it
            erro = ""
        }

        Text("Sexo", color = TextoPrimario)
        SeletorDuplo("Fêmea", "Macho", sexo) {
            sexo = it
            erro = ""
        }

        Text("Porte", color = TextoPrimario)
        SeletorTriplo("Pequeno", "Médio", "Grande", porte) {
            porte = it
            erro = ""
        }

        CampoForm(
            label = "Peso (kg)",
            value = pesoTexto,
            onValueChange = {
                val filtrado = it.filter { c -> c.isDigit() || c == ',' || c == '.' }
                pesoTexto = filtrado
                erro = ""
            }
        )

        CampoForm("Localização (opcional)", localizacao) {
            localizacao = it
        }

        Text("Idade aproximada: ${idade.toInt()} anos", color = TextoPrimario)

        Slider(
            value = idade,
            onValueChange = { idade = it },
            valueRange = 0f..20f
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Vacinado", color = TextoPrimario)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = vacinado,
                onCheckedChange = { vacinado = it }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Castrado", color = TextoPrimario)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = castrado,
                onCheckedChange = { castrado = it }
            )
        }

        CampoForm("Descrição (opcional)", descricao) {
            descricao = it
        }

        if (erro.isNotBlank()) {
            Text(
                text = erro,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BotaoPadrao(
            texto = "Salvar",
            onClick = {
                val pesoConvertido = pesoTexto.replace(",", ".").toFloatOrNull()

                when {
                    nome.isBlank() -> {
                        erro = "Informe o nome do pet."
                    }

                    imagemSelecionada == null -> {
                        erro = "Selecione uma foto para o pet."
                    }

                    pesoTexto.isBlank() || pesoConvertido == null || pesoConvertido <= 0f -> {
                        erro = "Informe um peso válido para o pet."
                    }

                    else -> {
                        val saude = buildString {
                            append(if (vacinado) "Vacinado" else "Não informado")
                            if (castrado) append(" e castrado")
                        }

                        val novoPet = Pet(
                            nome = nome,
                            tipo = tipo,
                            idade = idade.toInt(),
                            sexo = sexo,
                            peso = pesoConvertido,
                            porte = porte,
                            descricao = if (descricao.isBlank()) "Sem descrição informada." else descricao,
                            saude = saude,
                            localizacao = if (localizacao.isBlank()) "Localização não informada" else localizacao,
                            tutor = nomeTutor,
                            imagem = imagemSelecionada!!
                        )

                        onSalvarPet(novoPet)

                        nome = ""
                        tipo = "Gato"
                        sexo = "Fêmea"
                        porte = "Pequeno"
                        descricao = ""
                        localizacao = ""
                        vacinado = false
                        castrado = false
                        idade = 1f
                        pesoTexto = ""
                        imagemSelecionada = R.drawable.cat1
                        erro = ""
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun CampoForm(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CinzaCampo,
            unfocusedContainerColor = CinzaCampo,
            focusedIndicatorColor = BordaSuave,
            unfocusedIndicatorColor = BordaSuave
        )
    )
}

@Composable
private fun SeletorDuplo(
    opcao1: String,
    opcao2: String,
    selecionado: String,
    onSelecionar: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OpcaoChip(opcao1, selecionado == opcao1, { onSelecionar(opcao1) }, Modifier.weight(1f))
        OpcaoChip(opcao2, selecionado == opcao2, { onSelecionar(opcao2) }, Modifier.weight(1f))
    }
}

@Composable
private fun SeletorTriplo(
    opcao1: String,
    opcao2: String,
    opcao3: String,
    selecionado: String,
    onSelecionar: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OpcaoChip(opcao1, selecionado == opcao1, { onSelecionar(opcao1) }, Modifier.weight(1f))
        OpcaoChip(opcao2, selecionado == opcao2, { onSelecionar(opcao2) }, Modifier.weight(1f))
        OpcaoChip(opcao3, selecionado == opcao3, { onSelecionar(opcao3) }, Modifier.weight(1f))
    }
}

@Composable
private fun OpcaoChip(
    texto: String,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (selecionado) AmareloSuave else CinzaCampo,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = texto,
            color = TextoPrimario,
            modifier = Modifier.wrapContentWidth()
        )
    }
}