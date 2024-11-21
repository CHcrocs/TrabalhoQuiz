package com.example.trabalhoquiz.viewmodel

import PerguntaViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trabalhoquiz.model.database.dao.PerguntaDao

class PerguntaViewModelFactory(
    private val perguntaDao: PerguntaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerguntaViewModel::class.java)) {
            return PerguntaViewModel(perguntaDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}


