package com.example.appser.repository

import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList

interface PersonaRepository {

    suspend fun getPersonas():PersonaList

    suspend fun getPersonaById(id: Int): PersonaEntity

    suspend fun savePersona(personaEntity: PersonaEntity)

}