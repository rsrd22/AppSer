package com.example.appser.data.resource

import com.example.appser.data.local.PersonaDao
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.UsuarioList
import com.example.appser.data.model.relations.PersonaWithCuestionario

class PersonaDataSource(private val personaDao: PersonaDao) {
    suspend fun  getAllPersona(): PersonaList {
        return PersonaList(personaDao.getAllPersona())
    }
    suspend fun getPersona(id: Int): PersonaEntity {
        return personaDao.getPersona(id)
    }

    suspend fun savePersona(persona: PersonaEntity){
        personaDao.savePersona(persona)
    }

    suspend fun getPersonaWithCuestionario(id: Long): PersonaWithCuestionario{
        return personaDao.getPersonaWithCuestionario(id)
    }
}