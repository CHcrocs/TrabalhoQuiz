
package com.example.questionario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.questionario.model.database.dao.CategoriaDao

class CategoriaViewModelFactory(
    private val categoriaDao: CategoriaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoriaViewModel(categoriaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

