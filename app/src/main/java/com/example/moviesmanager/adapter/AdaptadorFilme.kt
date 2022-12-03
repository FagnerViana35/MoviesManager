package com.example.moviesmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moviesmanager.R
import com.example.moviesmanager.model.Filme

class AdaptadorFilme (
    context: Context,
    private val filmeList: MutableList<Filme>
) : ArrayAdapter<Filme>(context, R.layout.tile_filmes, filmeList) {
    private data class TileFilmeHolder(val nome: TextView, val lancamento: TextView, val genero: TextView, val duracao: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val filme = filmeList[position]
        var filmeTileView = convertView
        if (filmeTileView == null) {

            filmeTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_filmes,
                    parent,
                    false
                )

            val tileFilmeHolder = TileFilmeHolder(
                filmeTileView.findViewById(R.id.nomeFilme),
                filmeTileView.findViewById(R.id.anoLancamento),
                filmeTileView.findViewById(R.id.genero),
                filmeTileView.findViewById(R.id.duracao),
            )
            filmeTileView.tag = tileFilmeHolder
        }

        with(filmeTileView?.tag as TileFilmeHolder) {
            nome.text = filme.nome
            lancamento.text = filme.lancamento
            genero.text = filme.generoFilme
            duracao.text = filme.duracaoFilme
        }

        return filmeTileView
    }
}