package com.example.questionario.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionario.model.database.dao.PerguntaDao
import kotlinx.coroutines.launch
import com.example.questionario.model.entity.Pergunta


class PerguntaViewModel(private val perguntaDao: PerguntaDao) : ViewModel() {

    var listaPerguntas = mutableStateOf(listOf<Pergunta>())
        private set

    private fun carregarPerguntas() {
        viewModelScope.launch {
            listaPerguntas.value = perguntaDao.buscarTodos()
        }
    }

    fun adicionarPergunta(enunciado: String, respostaId: Int, categoriaId: Int): String {

        viewModelScope.launch {
            val pergunta = Pergunta(
                enunciado = enunciado,
                respostaId = respostaId,
                categoriaId = categoriaId
            )
            perguntaDao.inserirPergunta(pergunta)
            carregarPerguntas()
        }
        return "Salvo com sucesso"
    }
}
