package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.AdaptadorFilme
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant.EXTRA_FILMES
import com.example.moviesmanager.model.Constant.VIEW_FILMES
import com.example.moviesmanager.model.Filme

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listafilmes: MutableList<Filme> = mutableListOf()

    private lateinit var adaptadorFilme: AdaptadorFilme;

    private lateinit var marl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root);
        adicionaFilmesLista();

        adaptadorFilme = AdaptadorFilme(this, listafilmes)
        amb.filmesLV.adapter = adaptadorFilme

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val filme = result.data?.getParcelableExtra<Filme>(EXTRA_FILMES)

                filme?.let { _filme->
                    val position = listafilmes.indexOfFirst { it.id == _filme.id }
                    if (position != -1) {

                        listafilmes[position] = _filme
                    }
                    else {
                        listafilmes.add(_filme)
                    }
                    adaptadorFilme.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.filmesLV)

        amb.filmesLV.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val filme = listafilmes[position]
                val filmeIntent = Intent(this@MainActivity, FilmeActivity::class.java)
                filmeIntent.putExtra(EXTRA_FILMES, filme)
                filmeIntent.putExtra(VIEW_FILMES, true)
                startActivity(filmeIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addFilme -> {
                val intentIntegralActivity = Intent(this,FilmeActivity::class.java)
                marl.launch(intentIntegralActivity)
                true
            }
            else -> {false}
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_main_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when(item.itemId) {
            R.id.editFilme -> {
                val filme = listafilmes[position]
                val filmeIntent = Intent(this, FilmeActivity::class.java)
                filmeIntent.putExtra(EXTRA_FILMES, filme)
                filmeIntent.putExtra(VIEW_FILMES, false)
                marl.launch(filmeIntent)
                true
            }
            R.id.removeFilme -> {
                listafilmes.removeAt(position)
                adaptadorFilme.notifyDataSetChanged()

                true
            }
            else -> { false }
        }
    }

    private fun adicionaFilmesLista(){
        listafilmes.add(
            Filme(3, "Capitão América", "2011", "Marvel Studios", "Marvel", "124", false, 8.0, "Ação")
        )
        listafilmes.add(
            Filme(4, "Homem nas Trevas", "2016", null, "Ghost House Pictures", "88", true, 9.0, "Terror")
        )
    }
}