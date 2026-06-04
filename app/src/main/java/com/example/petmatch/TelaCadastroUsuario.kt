package com.example.petmatch

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.petmatch.ui.theme.BordaSuave
import com.example.petmatch.ui.theme.CinzaCampo
import com.example.petmatch.ui.theme.TextoPrimario
import com.example.petmatch.ui.theme.TextoSecundario

@Composable
fun TelaCadastroUsuario(
    onCriarContaClick: (String, String, String) -> Unit,
    onVoltarClick: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

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
                text = "Crie sua conta para continuar",
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Criar conta",
                style = MaterialTheme.typography.headlineMedium,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cadastre seus dados para acessar o PetMatch.",
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(24.dp))

            CampoCadastro(
                value = nome,
                onValueChange = {
                    nome = it
                    erro = ""
                },
                label = "Nome completo"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoCadastro(
                value = email,
                onValueChange = {
                    email = it
                    erro = ""
                },
                label = "E-mail",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoCadastro(
                value = senha,
                onValueChange = {
                    senha = it
                    erro = ""
                },
                label = "Senha",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            CampoCadastro(
                value = confirmarSenha,
                onValueChange = {
                    confirmarSenha = it
                    erro = ""
                },
                label = "Confirmar senha",
                isPassword = true
            )

            if (erro.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = erro,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Column {
            BotaoPadrao(
                texto = "Criar conta",
                onClick = {
                    erro = when {
                        nome.isBlank() -> "Informe seu nome."
                        email.isBlank() -> "Informe seu e-mail."
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Informe um e-mail válido."
                        senha.length < 4 -> "A senha precisa ter pelo menos 4 caracteres."
                        senha != confirmarSenha -> "As senhas não são iguais."
                        else -> ""
                    }

                    if (erro.isBlank()) {
                        onCriarContaClick(nome, email, senha)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotaoSecundario(
                texto = "Voltar para login",
                onClick = onVoltarClick
            )
        }
    }
}

@Composable
private fun CampoCadastro(
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
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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