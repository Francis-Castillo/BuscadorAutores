package com.dam.retrofitbuscadorautores

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritosActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rvFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val adaptador = AdaptadorMisFavs()
        recyclerView.adapter = adaptador

        adaptador.setAdaptadorAutoresCallback(object : AdaptadorMisFavs.AdaptadorFavoritosCallback {
            override fun onDeleteAutor(autor: Autores) {

                lifecycleScope.launch (Dispatchers.IO){  // usar siempre lifecyclescope en vez de coroutines
                    // recuperamos conexion a BBDD
                    val database = AutoresBBDD.getInstance(applicationContext)
                    //borramos el autor pasado
                    database.autoresDAO().deleteAutor(autor)
                    //recuperamos todos los autores que queda
                    var datos = database.autoresDAO().selectAll() as MutableList<Autores>  // para evitar fallos, si noi es una mutablelist de autores, muere

                    withContext(Dispatchers.Main) {
                        //enviamos la lista al adaptador
                        adaptador.changelist(datos)
                        //notificamos que el array ha cambiado
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        })

        lifecycleScope.launch (Dispatchers.IO){  // usar siempre lifecyclescope en vez de coroutines
            val database = AutoresBBDD.getInstance(applicationContext)
            var datos = database.autoresDAO().selectAll() as MutableList<Autores>  // para evitar fallos, si noi es una mutablelist de autores, muere

            withContext(Dispatchers.Main) {
                adaptador.changelist(datos)
                adaptador.notifyDataSetChanged()
            }
        }
    }
}