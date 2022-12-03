package com.example.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity
data class Filme(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @NonNull
    var nome: String,
    @NonNull
    var lancamento: String,
    var estudio: String?,
    var produtora: String?,
    @NonNull
    var duracaoFilme: String,
    @NonNull
    var assistido: Boolean,
    var notaFilme: Double?,
    @NonNull
    var generoFilme: String,
) : Parcelable
