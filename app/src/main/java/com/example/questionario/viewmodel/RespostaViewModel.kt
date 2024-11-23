package com.example.questionario.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionario.model.database.dao.CategoriaDao
import com.example.questionario.model.database.dao.PerguntaDao
import com.example.questionario.model.database.dao.RespostaDao
import com.example.questionario.model.entity.Categoria
import kotlinx.coroutines.launch
import com.example.questionario.model.entity.Pergunta
import com.example.questionario.model.entity.Resposta


class RespostaViewModel(private val respostaDao: RespostaDao) : ViewModel() {

    var listaCategoria = mutableStateOf(listOf<Resposta>())
        private set

    private fun carregarRespostas() {
        viewModelScope.launch {
            listaCategoria.value = respostaDao.buscarTodos()
        }
    }

    fun adicionarResposta(
        respostaCorreta: String,
        respostaIncorreta1: String,
        respostaIncorreta2: String,
        respostaIncorreta3: String
    ): String {

        viewModelScope.launch {
            val resposta = Resposta(
                respostaCorreta = respostaCorreta,
                respostasIncorreta1 = respostaIncorreta1,
                respostasIncorreta2 = respostaIncorreta2,
                respostasIncorreta3 = respostaIncorreta3
            )
            respostaDao.inserirResposta(resposta)
            carregarRespostas()
        }
        return "Salvo com sucesso"
    }
}