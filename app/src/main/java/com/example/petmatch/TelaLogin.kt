package com.example.petmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.AmareloSuave
import com.example.petmatch.ui.theme.BordaSuave
import com.example.petmatch.ui.theme.CinzaCampo
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaLogin(
    onEntrarClick: (String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var aceitouTermos by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(AmareloSuave, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PetMatch",
                    color = TextoPrimario,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Entrar",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bem-vindo! Insira suas informações para começar.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(24.dp))

            CampoPadrao(
                value = nome,
                onValueChange = { nome = it },
                label = "Seu nome"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoPadrao(
                value = email,
                onValueChange = { email = it },
                label = "Seu email",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoPadrao(
                value = senha,
                onValueChange = { senha = it },
                label = "Senha",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = aceitouTermos,
                    onCheckedChange = { aceitouTermos = it }
                )
                Text(
                    text = "Eu aceito os termos de uso e condições.",
                    color = TextoSecundario,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Column {
            BotaoPadrao(
                texto = "Entrar",
                onClick = {
                    if (nome.isNotBlank() && aceitouTermos) {
                        onEntrarClick(nome)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Não tem conta? Criar conta!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = TextoSecundario
            )
        }
    }
}

@Composable
private fun CampoPadrao(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CinzaCampo,
            unfocusedContainerColor = CinzaCampo,
            focusedIndicatorColor = BordaSuave,
            unfocusedIndicatorColor = BordaSuave,
            focusedLabelColor = TextoSecundario,
            unfocusedLabelColor = TextoSecundario
        )
    )
}