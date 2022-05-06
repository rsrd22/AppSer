package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.CategoriasEntity
import com.example.appser.repository.CategoriasRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CategoriasViewModel(private val repo: CategoriasRepository): ViewModel() {

    fun fetchSaveCategoria(categoria: CategoriasEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.saveCategoria(categoria)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchAllCategorias() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllCategorias()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchCategoriabyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCategoriaById(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class CategoriasViewModelFactory(private val repo:CategoriasRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CategoriasRepository::class.java).newInstance(repo)
    }
}