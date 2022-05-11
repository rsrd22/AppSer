package com.example.appser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appser.data.model.relations.PersonaAndUsuario

class MainViewModel: ViewModel() {
    private val personaUsuario= MutableLiveData<PersonaAndUsuario>()

    fun setPersonaAndUsuario(_personaAndUsuario: PersonaAndUsuario){
        personaUsuario.value = _personaAndUsuario
    }

    fun getPersonaAndUsuario(): LiveData<PersonaAndUsuario> {
        return personaUsuario
    }
}