package com.example.moviesmanager.model.dao

import androidx.room.*
import com.example.moviesmanager.model.entity.Filme

@Dao
interface FilmeRoomDAO {
    companion object Constant{
        const val FILME_DATABASE_FILE = "filmes_room"
        const val FILME_TABLE = "filme"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "nome"
    }
    @Insert
    fun createFilme(filme: Filme)

    @Query("SELECT * FROM $FILME_TABLE WHERE $ID_COLUMN = :id")
    fun retrieveFilme(id: Int): Filme?


    @Query("SELECT * FROM $FILME_TABLE ORDER BY $NAME_COLUMN")
    fun retrieveFilme():MutableList<Filme>

    @Update
    fun updateFilme(filme: Filme): Int

    @Delete
    fun deleteFilme(filme: Filme): Int
}