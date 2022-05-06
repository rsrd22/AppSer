package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.PreguntasEntity
import com.example.appser.repository.PreguntasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle

class PreguntasViewModel(private val repo: PreguntasRepository): ViewModel() {

    fun fetchAllPreguntas() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllPreguntas()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchPreguntabyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getPreguntabyId(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchSavePregunta(pregunta: PreguntasEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.savePregunta(pregunta)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class PreguntasViewModelFactory(private val repo:PreguntasRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PreguntasRepository::class.java).newInstance(repo)
    }
}