package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appser.repository.CuestionarioPreguntasRepository

class CuestionarioPreguntasViewModel(private val repo: CuestionarioPreguntasRepository): ViewModel() {



}

class CuestionarioPreguntaViewModelFactory(private val repo: CuestionarioPreguntasRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CuestionarioPreguntasRepository::class.java).newInstance(repo)
    }
}