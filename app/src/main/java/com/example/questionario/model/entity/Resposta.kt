package com.example.questionario.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "respostas")
data class Resposta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val respostaCorreta: String,
    val respostasIncorreta1: String,
    val respostasIncorreta2: String,
    val respostasIncorreta3: String,
)
