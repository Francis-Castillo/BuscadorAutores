package com.dam.retrofitbuscadorautores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorMisFavs : RecyclerView.Adapter<FavoritosViewHolder>(){
    private var favs: List<Autores> =mutableListOf()
    private lateinit var listener : AdaptadorFavoritosCallback

    interface AdaptadorFavoritosCallback {
        fun onDeleteAutor(autor: Autores)
    }

    fun setAdaptadorAutoresCallback(listener: AdaptadorFavoritosCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritosViewHolder(layoutInflater.inflate(R.layout.fila_autores,
            parent, false))
    }
    override fun getItemCount(): Int = favs.size
    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item = favs[position]
        holder.nombre.text = item.name
        holder.toolbarFavorito.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.action_eliminar->{
                    listener.onDeleteAutor(item)
                    true
                }

                else -> {true}
            }

        }

    }

    fun changelist(autores: List<Autores>) {
        favs=autores
    }
}