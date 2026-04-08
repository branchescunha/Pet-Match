package com.example.petmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.AmareloPrincipal
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaEscolhaPerfil(
    nomeUsuario: String,
    onContinuarClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(AmareloSuave, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PetMatch",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoPrimario
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Olá, $nomeUsuario",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecione seu perfil no aplicativo",
                style = MaterialTheme.typography.bodyLarge,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(28.dp))

            CardPerfil(
                titulo = "Tutor",
                subtitulo = "Deseja divulgar pets para adoção",
                onClick = { onContinuarClick("Tutor") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CardPerfil(
                titulo = "Cliente",
                subtitulo = "Deseja encontrar um pet para adotar",
                onClick = { onContinuarClick("Cliente") }
            )
        }
    }
}

@Composable
private fun CardPerfil(
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Branco),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(AmareloPrincipal, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )
        }
    }
}