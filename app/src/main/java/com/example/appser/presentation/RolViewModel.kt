package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.RolEntity
import com.example.appser.repository.RolRepository
import com.example.appser.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers

class RolViewModel(private val repo: RolRepository): ViewModel() {

    fun fetchSaveRol(rol: RolEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveRol(rol)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchAllRol()= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getRoles()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchRolbyId(id: Long)= liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getRolById(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchCountRol() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getCountRol()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class RolViewModelFactory(private val repo: RolRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RolRepository::class.java).newInstance(repo)
    }
}