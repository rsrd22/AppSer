package com.example.appser.repository

import com.example.appser.data.model.HistoricoCuestionario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.relations.PersonaWithCuestionario
import com.example.appser.data.resource.PersonaDataSource

class PersonaRepositoryImpl(private val personaDataSource: PersonaDataSource): PersonaRepository {
    override suspend fun getPersonas(): PersonaList {
        return personaDataSource.getAllPersona()
    }

    override suspend fun getPersonaById(id: Int): PersonaEntity {
        return personaDataSource.getPersona(id)
    }

    override suspend fun savePersona(personaEntity: PersonaEntity) {
        return personaDataSource.savePersona(personaEntity)
    }

    override suspend fun getPersonaWithCuestionario(id: Long): PersonaWithCuestionario {
        return personaDataSource.getPersonaWithCuestionario(id)
    }

    override suspend fun getHistoricoCuestionario(id: Long): List<HistoricoCuestionario> {
        return personaDataSource.getHistoricoCuestionario(id)
    }
}