package com.example.moviesmanager.controller

import android.os.AsyncTask
import androidx.room.Room
import com.example.moviesmanager.model.dao.FilmeRoomDAO
import com.example.moviesmanager.model.dao.FilmeRoomDAO.Constant.FILME_DATABASE_FILE
import com.example.moviesmanager.model.datadase.FilmeRoomDaoDatabase
import com.example.moviesmanager.model.entity.Filme
import com.example.moviesmanager.view.MainActivity

class FilmeRoomController(private val mainActivity: MainActivity) {
    private val filmeDaoImpl: FilmeRoomDAO by lazy {
        Room.databaseBuilder(
            mainActivity,
            FilmeRoomDaoDatabase::class.java,
            FILME_DATABASE_FILE
        ).build().getFilmeRoomDao()
    }

    fun insertFilme(filme: Filme) {
        Thread{
            filmeDaoImpl.createFilme(filme)
            getFilmes()
        }.start()

    }

    fun getFilmes() {
        object : AsyncTask<Unit, Unit, MutableList<Filme>>() {
            override fun doInBackground(vararg p0: Unit?): MutableList<Filme> {
                val returnList = mutableListOf<Filme>()
                returnList.addAll(filmeDaoImpl.retrieveFilmes())
                return returnList
            }
            override fun onPostExecute(result: MutableList<Filme>?) {
                super.onPostExecute(result)
                if(result != null){
                    mainActivity.updateFilmeList(result)
                }
            }
        }.execute()
    }
    fun getFilmes(id: Int) = filmeDaoImpl.retrieveFilme(id)

    fun editFilmes(filme: Filme) {
        Thread{
            filmeDaoImpl.updateFilme(filme)
            getFilmes()
        }.start()
    }

    fun removeFilmes(filme: Filme) {
        Thread{
            filmeDaoImpl.deleteFilme(filme)
            getFilmes()
        }.start()
    }
}