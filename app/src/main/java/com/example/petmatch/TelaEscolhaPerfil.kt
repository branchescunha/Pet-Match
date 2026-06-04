package com.example.petmatch

import androidx.compose.foundation.background
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
            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = "🐾 PetMatch",
                style = MaterialTheme.typography.headlineLarge,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Escolha como deseja usar o app",
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(36.dp))

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
                icone = "🏠",
                titulo = "Tutor",
                subtitulo = "Cadastre pets, acompanhe informações e ajude animais a encontrarem um novo lar.",
                onClick = { onContinuarClick("Tutor") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CardPerfil(
                icone = "❤️",
                titulo = "Cliente",
                subtitulo = "Explore pets disponíveis, favorite animais e encontre seu novo companheiro.",
                onClick = { onContinuarClick("Cliente") }
            )
        }
    }
}

@Composable
private fun CardPerfil(
    icone: String,
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Branco),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(58.dp)
                    .background(AmareloSuave, CircleShape),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = icone,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column {
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
}