package com.example.questionario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questionario.model.database.dao.RespostaDao

class RespostaViewModelFactory(
    private val respostaDao: RespostaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RespostaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RespostaViewModel(respostaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


