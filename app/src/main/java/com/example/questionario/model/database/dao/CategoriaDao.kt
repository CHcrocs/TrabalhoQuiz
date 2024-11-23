package com.example.questionario.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.questionario.model.entity.Categoria

@Dao
interface CategoriaDao {

    @Insert
    fun inserirCategoria(categoria: Categoria)

    @Query("SELECT * FROM perguntas")
    suspend fun buscarTodos(): List<Categoria>
}