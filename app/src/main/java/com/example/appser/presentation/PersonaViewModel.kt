package com.example.appser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.appser.core.Resource
import com.example.appser.data.model.PersonaEntity
import com.example.appser.repository.PersonaRepository
import com.example.appser.repository.PersonaRepositoryImpl
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PersonaViewModel(private val repo: PersonaRepository): ViewModel() {
    fun fetchSavePersona(persona: PersonaEntity) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.savePersona(persona)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchPersonaWithCuestionario(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repo.getPersonaWithCuestionario(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class PersonaViewModelFactory(private val repo: PersonaRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PersonaRepository::class.java).newInstance(repo)
    }
}