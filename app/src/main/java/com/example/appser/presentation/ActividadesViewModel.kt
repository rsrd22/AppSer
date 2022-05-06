package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.repository.ActividadesRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ActividadesViewModel(private val repo: ActividadesRepository): ViewModel() {

    fun fetchAllActividades() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getAllActividades()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchActividadbyId(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getActividadbyId(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchSaveActividad(actividadesEntity: ActividadesEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveActividad(actividadesEntity)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class ActividadesViewModelFactory(private val repo: ActividadesRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ActividadesRepository::class.java).newInstance(repo)
    }
}