package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.moviesmanager.R
import com.example.moviesmanager.databinding.ActivityFilmeBinding
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant.EXTRA_FILMES
import com.example.moviesmanager.model.Constant.VIEW_FILMES
import com.example.moviesmanager.model.Filme
import java.util.*

class FilmeActivity : AppCompatActivity() {

    private val afb: ActivityFilmeBinding by lazy{
        ActivityFilmeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val receivedFilme = intent.getParcelableExtra<Filme>(EXTRA_FILMES)
        receivedFilme?.let{ _receivedFilme ->
            with(afb) {
                with(_receivedFilme) {
                    nomeEt.setText(nome)
                    lancamentoEt.setText(lancamento.toString())
                    estudioEt.setText(estudio).toString()
                    produtoraEt.setText(produtora).toString()
                    duracaoFilmeEt.setText(duracaoFilme).toString()
                    if(assistido){
                        assistidoCk.isChecked = true
                    }else{
                        false
                    }
                    notaFilmeEt.setText(notaFilme.toString())
                }
            }
        }

        val viewFilmes = intent.getBooleanExtra(VIEW_FILMES, false)
        if (viewFilmes) {
            afb.nomeEt.isEnabled = false
            afb.notaFilmeEt.isEnabled = false
            afb.duracaoFilmeEt.isEnabled = false
            afb.produtoraEt.isEnabled = false
            afb.lancamentoEt.isEnabled = false
            afb.generoFilmeSp.isEnabled = false
            afb.assistidoCk.isEnabled = false
            afb.saveFilmeBt.isEnabled = false
        }

        afb.saveFilmeBt.setOnClickListener {
            val filme = Filme(
                id = receivedFilme?.id?:Random(System.currentTimeMillis()).nextInt(),
                nome = afb.nomeEt.text.toString(),
                lancamento = afb.lancamentoEt.text.toString(),
                estudio = afb.estudioEt.text.toString(),
                produtora = afb.produtoraEt.text.toString(),
                duracaoFilme = afb.duracaoFilmeEt.text.toString(),
                assistido = afb.assistidoCk.isChecked,
                notaFilme = afb.notaFilmeEt.text.toString().toDouble(),
                generoFilme = afb.generoFilmeSp.toString(),
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_FILMES, filme)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}