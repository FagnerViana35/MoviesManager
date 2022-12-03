package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.moviesmanager.R
import com.example.moviesmanager.databinding.ActivityFilmeBinding
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant.EXTRA_FILMES
import com.example.moviesmanager.model.Constant.VIEW_FILMES
import com.example.moviesmanager.model.Filme
import java.text.FieldPosition
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
            afb.estudioEt.isEnabled = false
            afb.produtoraEt.isEnabled = false
            afb.lancamentoEt.isEnabled = false
            afb.generoFilmeSp.isEnabled = false
            afb.assistidoCk.isEnabled = false
            afb.saveFilmeBt.isEnabled = false

            afb.saveFilmeBt.visibility = View.GONE
        }

        var selectGenre: String = "";

        afb.generoFilmeSp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectGenre = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        afb.saveFilmeBt.setOnClickListener {
            var nota = if (afb.notaFilmeEt.text.toString() == "") null else afb.notaFilmeEt.text.toString().toDouble()

            val filme = Filme(
                id = receivedFilme?.id?:Random(System.currentTimeMillis()).nextInt(),
                nome = afb.nomeEt.text.toString(),
                lancamento = afb.lancamentoEt.text.toString(),
                estudio = afb.estudioEt.text.toString(),
                produtora = afb.produtoraEt.text.toString(),
                duracaoFilme = afb.duracaoFilmeEt.text.toString(),
                assistido = afb.assistidoCk.isChecked,
                notaFilme = nota,
                generoFilme = selectGenre,
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_FILMES, filme)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}