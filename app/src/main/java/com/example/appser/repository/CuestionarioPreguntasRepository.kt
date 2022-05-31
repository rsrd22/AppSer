package com.example.appser.repository

import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.CuestionarioPreguntasList

interface CuestionarioPreguntasRepository {

    suspend fun getAllCuestionarioPreguntas(): CuestionarioPreguntasList

    suspend fun getCuestionarioPreguntabyId(id: Long): CuestionarioPreguntasEntity

    suspend fun saveCuestionarioPregunta(cuestionarioPregunta: CuestionarioPreguntasEntity)

    suspend fun saveCuestionarioWithCuestionarioPregunta(cuestionario: CuestionarioEntity): Long
}