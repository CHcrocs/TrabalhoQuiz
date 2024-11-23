package com.example.questionario.model.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "perguntas",
    foreignKeys = [ForeignKey(
        entity = Resposta::class,
        parentColumns = ["id"],
        childColumns = ["respostaId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Resposta::class,
        parentColumns = ["id"],
        childColumns = ["categoriaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Pergunta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val enunciado: String,
    @ColumnInfo(name = "respostaId") val respostaId: Int,
    @ColumnInfo(name = "categoriaId") val categoriaId: Int
)
