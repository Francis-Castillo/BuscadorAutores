package com.dam.retrofitbuscadorautores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorAutores : RecyclerView.Adapter<AutoresViewHolder>(){
    private var aut: List<Autores> =mutableListOf()
    private lateinit var listener : AdaptadorAutoresCallback

    interface AdaptadorAutoresCallback {
        fun onSaveAutor(autor: Autores)
    }

    fun setAdaptadorAutoresCallback(listener: AdaptadorAutoresCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AutoresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AutoresViewHolder(layoutInflater.inflate(R.layout.fila_autores,
            parent, false))
    }
    override fun getItemCount(): Int = aut.size
    override fun onBindViewHolder(holder: AutoresViewHolder, position: Int) { // se encarga de rellenar cad tarjeta de la lista con sus datos reales y configurar que ocurre cuando el usuario interactua con ella
        val item = aut[position]
        holder.nombre.text = item.name
        holder.toolbarFavorito.setOnMenuItemClickListener{ // vigila si se pulsa la acción dentro de esa actividad (en este caso favorito)
            when(it.itemId){ // it representa el item pulsado
                R.id.action_favoritos->{
                    listener.onSaveAutor(item)
                    true
                }

                else -> {true}
            }

        }

    }

    fun changelist(autores: List<Autores>) {
        aut=autores
    }
}