import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perguntas")
data class Pergunta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val enunciado: String,
    val respostaCorreta: String,
    val respostaIncorreta1: String,
    val respostaIncorreta2: String,
    val respostaIncorreta3: String,
    val categoria: String
)
