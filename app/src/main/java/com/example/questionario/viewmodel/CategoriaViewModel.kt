package com.example.questionario.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionario.model.database.dao.CategoriaDao
import com.example.questionario.model.database.dao.PerguntaDao
import com.example.questionario.model.entity.Categoria
import kotlinx.coroutines.launch
import com.example.questionario.model.entity.Pergunta


class CategoriaViewModel(private val categoriaDao: CategoriaDao) : ViewModel() {

    var listaCategoria = mutableStateOf(listOf<Categoria>())
        private set

    private fun carregarCategorias() {
        viewModelScope.launch {
            listaCategoria.value = categoriaDao.buscarTodos()
        }
    }

    fun adicionarCategoria(nome: String): String {

        viewModelScope.launch {
            val categoria = Categoria(
                nome = nome
            )
            categoriaDao.inserirCategoria(categoria)
            carregarCategorias()
        }
        return "Salvo com sucesso"
    }
}