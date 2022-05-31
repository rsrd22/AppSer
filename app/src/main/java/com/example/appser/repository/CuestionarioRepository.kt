package com.example.appser.repository

import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioList

interface CuestionarioRepository {

    suspend fun getAllCuestionario(): CuestionarioList

    suspend fun getCuestionariobyId(id: Long): CuestionarioEntity

    suspend fun saveCuestionario(cuestionario: CuestionarioEntity)

    suspend fun updateCuestionario(cuestionarioId: Long, actividaAsignadaId: Long)
}