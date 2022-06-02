package com.example.appser.repository

import com.example.appser.data.model.HistoricoCuestionario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.relations.PersonaWithCuestionario

interface PersonaRepository {

    suspend fun getPersonas():PersonaList

    suspend fun getPersonaById(id: Int): PersonaEntity

    suspend fun savePersona(personaEntity: PersonaEntity)

    suspend fun getPersonaWithCuestionario(id: Long): PersonaWithCuestionario

    suspend fun getHistoricoCuestionario(id: Long): List<HistoricoCuestionario>

}