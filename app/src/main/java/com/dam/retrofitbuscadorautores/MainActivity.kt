package com.dam.retrofitbuscadorautores

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.retrofitbuscadorautores.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var recy: RecyclerView
    val adaptadorRecyclerView = AdaptadorAutores()
    private lateinit var buscador: SearchView


    //viewBinding
    private lateinit var binding : ActivityMainBinding


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_misfavoritos -> {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // Opcional para bindear automaticamente (esta linea y la siguiente)
        setContentView(binding.root)

        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)  // al hacer el binding antes, esta línea se queda obsoleta, pero de momento seguir usando la forma tradicional
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //menu
        setSupportActionBar(findViewById(R.id.toolbarPrincipal))

        binding

        //bindeo
        buscador=findViewById(R.id.svAutores)
        recy = findViewById(R.id.rvAutores)
        recy.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )
        recy.adapter = adaptadorRecyclerView

        buscador.setOnQueryTextListener(this)

        adaptadorRecyclerView.setAdaptadorAutoresCallback(object : AdaptadorAutores.AdaptadorAutoresCallback {
            override fun onSaveAutor(autor: Autores) {
                CoroutineScope(Dispatchers.IO).launch {
                    val database = AutoresBBDD.getInstance(applicationContext)
                    database.autoresDAO().insertAutor(autor)
                }
            }

        })

    }

    private fun busquedaAutores(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            //nuestra peticion a la api
            val call = RetrofitClient.api.getAutores("/search/authors.json?q=$query")
            var autoresAPI = call.body() // solo recogemos el cuerpo de la peticion
            if (call.isSuccessful) {
                // proceso los datos
                val autores = autoresAPI?.autores ?: emptyList()
                withContext(Dispatchers.Main) {
                    adaptadorRecyclerView.changelist(autores)
                    adaptadorRecyclerView.notifyDataSetChanged()

                }
            }
        }
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(cadena: String?): Boolean {
        if (!cadena.isNullOrEmpty()) {
            busquedaAutores(cadena.lowercase())
        }

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.buscador.windowToken, 0)
        return true
    }
}