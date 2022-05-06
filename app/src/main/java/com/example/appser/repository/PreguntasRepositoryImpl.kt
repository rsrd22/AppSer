package com.example.appser.repository

import com.example.appser.data.model.PreguntasEntity
import com.example.appser.data.model.PreguntasList
import com.example.appser.data.resource.PreguntasDataSource

class PreguntasRepositoryImpl(private val preguntasDataSource: PreguntasDataSource): PreguntasRepository {
    override suspend fun getAllPreguntas(): PreguntasList {
        return preguntasDataSource.getAllPreguntas()
    }

    override suspend fun getPreguntabyId(id: Long): PreguntasEntity {
        return preguntasDataSource.getPreguntabyId(id)
    }

    override suspend fun savePregunta(pregunta: PreguntasEntity) {
        return preguntasDataSource.savePregunta(pregunta)
    }
}