package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.repository.EmocionesRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EmocionesViewModel(private val repo: EmocionesRepository): ViewModel() {

    fun fetchAllEmociones() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllEmociones()))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchEmocionesbyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getEmocionbyId(id)))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchSaveEmocion(emocion: EmocionesEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveEmocion(emocion)))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

class EmocionesViewModelFactory(private val repo: EmocionesRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EmocionesRepository::class.java).newInstance(repo)
    }
}