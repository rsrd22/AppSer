package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers

class UsuarioViewModel(private val repo: UsuarioRepository): ViewModel() {

    fun fetchSaveUsuario(usuario: UsuarioEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.saveUsuario(usuario)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchPersonaWithUsuario(persona: PersonaEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.insertPersonaWithUsuario(persona)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchPersonaAndUsuario() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getPersonasAndUsuario()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchUsuarioByEmail(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getUsuarioByEmail(email)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class UsuarioViewModelFactory(private val repo: UsuarioRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UsuarioRepository::class.java).newInstance(repo)
    }
}