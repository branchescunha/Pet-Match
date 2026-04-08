package com.example.petmatch

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Pet(
    val nome: String,
    val tipo: String,
    val idade: Int,
    val sexo: String,
    val peso: Float,
    val porte: String,
    val descricao: String,
    val saude: String,
    val localizacao: String,
    val tutor: String,
    @DrawableRes val imagem: Int
) : Serializable