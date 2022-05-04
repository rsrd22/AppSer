package com.example.appser.repository

import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioList
import com.example.appser.data.resource.CuestionarioDataSource

class CuestionarioRepositoryImpl(private val cuestionarioDataSource: CuestionarioDataSource): CuestionarioRepository {
    override suspend fun getAllCuestionario(): CuestionarioList {
        return cuestionarioDataSource.getAllCuestionario()
    }

    override suspend fun getCuestionariobyId(id: Long): CuestionarioEntity {
        return cuestionarioDataSource.getCuestionariobyId(id)
    }

    override suspend fun saveCuestionario(cuestionario: CuestionarioEntity) {
        return cuestionarioDataSource.saveCuestionario(cuestionario)
    }
}