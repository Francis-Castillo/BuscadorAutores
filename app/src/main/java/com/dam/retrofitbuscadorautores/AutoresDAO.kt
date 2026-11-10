package com.dam.retrofitbuscadorautores

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface AutoresDAO {
    @Query("SELECT * FROM autores")
    fun selectAll() : List<Autores>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    // cuando hago un insert, si el id ya existe, elimina el anterior y añade el nuevo
    fun insertAutor(autores: Autores) : Long

    @Delete
    fun deleteAutor(autores: Autores)
}