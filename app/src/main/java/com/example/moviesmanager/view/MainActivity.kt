package com.example.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.AdaptadorFilme
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Filme

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listafilmes: MutableList<Filme> = mutableListOf()

    private lateinit var adaptadorFilme: AdaptadorFilme;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root);
        adicionaFilmesLista();

        adaptadorFilme = AdaptadorFilme(this, listafilmes)
        amb.filmesLV.adapter = adaptadorFilme
    }

    private fun adicionaFilmesLista(){
        listafilmes.add(
            Filme(1, "Homem Aranha", "2002", "Marvel Studios", "Marvel", "121", true, 9.0, "Ação")
        )
        listafilmes.add(
            Filme(2, "Adão Negro", "2022", "DC", "DC Filmes", "125", true, 10.0, "Ação")
        )
        listafilmes.add(
            Filme(3, "Capitão América", "2011", "Marvel Studios", "Marvel", "124", false, 8.0, "Ação")
        )
        listafilmes.add(
            Filme(4, "Homem nas Trevas", "2016", null, "Ghost House Pictures", "88", true, 9.0, "Terror")
        )
    }
}