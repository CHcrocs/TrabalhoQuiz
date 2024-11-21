package com.example.trabalhoquiz.model.database.dao

import Pergunta
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PerguntaDao {

    @Insert
    fun inserirPergunta(pergunta: Pergunta)

    @Query("SELECT * FROM perguntas")
    suspend fun buscarTodos(): List<Pergunta>

}