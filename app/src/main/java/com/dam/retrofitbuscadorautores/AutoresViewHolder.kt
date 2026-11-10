package com.dam.retrofitbuscadorautores

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class AutoresViewHolder (view: View): RecyclerView.ViewHolder(view) {
    var nombre: TextView
    var toolbarFavorito : Toolbar
    init{
        nombre=view.findViewById(R.id.tvAutor)
        toolbarFavorito=view.findViewById(R.id.toolbarFavorito)
        toolbarFavorito.inflateMenu(R.menu.menu_favoritos)
    }
}
