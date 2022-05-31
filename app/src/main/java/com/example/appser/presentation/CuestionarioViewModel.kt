package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.repository.CuestionarioRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CuestionarioViewModel(private val repo: CuestionarioRepository): ViewModel() {

    fun fetchgetAllCuestionario() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllCuestionario()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchgetCuestionariobyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCuestionariobyId(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchSaveCuestionario(cuestionario: CuestionarioEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveCuestionario(cuestionario)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchUpdateCuestionario(cuestionarioId: Long, actividadAsignadaId: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.updateCuestionario(cuestionarioId, actividadAsignadaId)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class CuestionarioViewModelFactory(private val repo: CuestionarioRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CuestionarioRepository::class.java).newInstance(repo)
    }
}