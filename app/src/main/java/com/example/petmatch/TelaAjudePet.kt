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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.Branco
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

data class LocalAjuda(
    val nome: String,
    val necessidade: String,
    val pix: String
)

@Composable
fun TelaAjudePet() {
    val locais = listOf(
        LocalAjuda(
            nome = "Abrigo Amor Animal",
            necessidade = "Ração, areia e medicamentos",
            pix = "amoranimal@pix.com"
        ),
        LocalAjuda(
            nome = "Lar Temporário Patinhas",
            necessidade = "Cobertas, ração e produtos de limpeza",
            pix = "patinhas@pix.com"
        ),
        LocalAjuda(
            nome = "ONG Acolher Pets",
            necessidade = "Ajuda financeira e doações",
            pix = "acolherpets@pix.com"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Ajude um Pet",
            style = MaterialTheme.typography.headlineMedium,
            color = TextoPrimario
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Veja locais que precisam de apoio agora.",
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(locais) { local ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                        Text(
                            text = "Necessidade: ${local.necessidade}",
                            color = TextoSecundario
                        )
                        Text(
                            text = "PIX: ${local.pix}",
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}