package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.AdaptadorFilme
import com.example.moviesmanager.controller.FilmeRoomController
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant.EXTRA_FILMES
import com.example.moviesmanager.model.Constant.VIEW_FILMES
import com.example.moviesmanager.model.entity.Filme

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val filmeController: FilmeRoomController by lazy {
        FilmeRoomController(this)
    }

    private val listafilmes: MutableList<Filme> = mutableListOf()

    private lateinit var adaptadorFilme: AdaptadorFilme;

    private lateinit var marl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root);
        filmeController.getFilmes();
        adaptadorFilme = AdaptadorFilme(this, listafilmes)
        amb.filmesLV.adapter = adaptadorFilme

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val filme = result.data?.getParcelableExtra<Filme>(EXTRA_FILMES)
                filme?.let { _filme ->
                    val position = listafilmes.indexOfFirst { it.id == _filme.id }
                    val positionNome = listafilmes.indexOfFirst { it.nome == _filme.nome }

                        if (position != -1) {
                            filmeController.editFilmes(_filme)
                        }
                    else {
                        if(positionNome == -1){
                            filmeController.insertFilme(_filme)
                        }else{
                            Toast.makeText(this, "Nome de filme já existe, favor tentar novamente com outro nome!", Toast.LENGTH_SHORT).show()
                        }


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
        val filme = listafilmes[position]

        return when(item.itemId) {
            R.id.editFilme -> {
                val filmeIntent = Intent(this, FilmeActivity::class.java)
                filmeIntent.putExtra(EXTRA_FILMES, filme)
                filmeIntent.putExtra(VIEW_FILMES, false)
                marl.launch(filmeIntent)
                true
            }
            R.id.removeFilme -> {
                filmeController.removeFilmes(filme)
                adaptadorFilme.notifyDataSetChanged()

                true
            }
            else -> { false }
        }
    }

    fun updateFilmeList(_filmeList: MutableList<Filme>){
        listafilmes.clear()
        listafilmes.addAll(_filmeList)
        adaptadorFilme.notifyDataSetChanged()
    }
}