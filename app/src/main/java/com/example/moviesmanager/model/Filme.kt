package com.example.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
data class Filme(

    val id: Number,
    var nome: String,
    var lancamento: String,
    var estudio: String?,
    var produtora: String?,
    var duracaoFilme: String,
    var assistido: Boolean,
    var notaFilme: Double?,
    var generoFilme: String,
) : Parcelable
