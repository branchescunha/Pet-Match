package com.example.petmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaBoasVindas(
    onEntrarClick: () -> Unit,
    onExplorarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🐾 PetMatch",
                style = MaterialTheme.typography.headlineLarge,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Conectando pets e famílias",
                style = MaterialTheme.typography.bodyLarge,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Adote com responsabilidade",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Encontre pets disponíveis para adoção, conheça suas histórias e conecte-se com tutores próximos.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextoSecundario,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            CardInfoBoasVindas(
                icone = "🐶",
                titulo = "Adote",
                descricao = "Encontre um novo amigo para sua família"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CardInfoBoasVindas(
                icone = "📍",
                titulo = "Localize",
                descricao = "Veja onde o pet está através do mapa"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CardInfoBoasVindas(
                icone = "🤝",
                titulo = "Ajude",
                descricao = "Apoie instituições parceiras e ONGs"
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            BotaoPadrao(
                texto = "Entrar",
                onClick = onEntrarClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotaoSecundario(
                texto = "Explorar sem login",
                onClick = onExplorarClick
            )
        }
    }
}

@Composable
private fun CardInfoBoasVindas(
    icone: String,
    titulo: String,
    descricao: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AmareloSuave,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icone,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.size(14.dp))

        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrimario
            )

            Text(
                text = descricao,
                color = TextoSecundario
            )
        }
    }
}