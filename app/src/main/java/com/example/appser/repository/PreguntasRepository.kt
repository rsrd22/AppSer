package com.example.appser.repository

import com.example.appser.data.model.PreguntasEntity
import com.example.appser.data.model.PreguntasList

interface PreguntasRepository {
    suspend fun getAllPreguntas(): PreguntasList

    suspend fun getPreguntabyId(id: Long): PreguntasEntity

    suspend fun savePregunta(pregunta: PreguntasEntity)
}