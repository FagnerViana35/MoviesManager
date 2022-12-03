package com.example.moviesmanager.model.datadase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesmanager.model.dao.FilmeRoomDAO
import com.example.moviesmanager.model.entity.Filme

@Database(entities = [Filme::class], version = 1)
abstract class FilmeRoomDaoDatabase: RoomDatabase() {
    abstract fun getFilmeRoomDao(): FilmeRoomDAO
}