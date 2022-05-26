package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.repository.CuestionarioPreguntasRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CuestionarioPreguntasViewModel(private val repo: CuestionarioPreguntasRepository): ViewModel() {

    fun fetchAllCuestionarioPreguntas() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllCuestionarioPreguntas()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchCuestionarioPreguntasbyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCuestionarioPreguntabyId(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchSaveCuestionarioPregunta(cuestionariopregunta: CuestionarioPreguntasEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveCuestionarioPregunta(cuestionariopregunta)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }




}

class CuestionarioPreguntaViewModelFactory(private val repo: CuestionarioPreguntasRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CuestionarioPreguntasRepository::class.java).newInstance(repo)
    }
}