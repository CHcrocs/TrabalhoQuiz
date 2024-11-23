package com.example.questionario.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.questionario.model.entity.Resposta

@Dao
interface RespostaDao {

    @Insert
    fun inserirResposta(resposta: Resposta)

    @Query("SELECT * FROM perguntas")
    suspend fun buscarTodos(): List<Resposta>
}