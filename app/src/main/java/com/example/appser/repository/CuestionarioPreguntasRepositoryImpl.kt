package com.example.appser.repository

import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.CuestionarioPreguntasList
import com.example.appser.data.resource.CuestionarioPreguntasDataSource

class CuestionarioPreguntasRepositoryImpl(private val cuestionarioPreguntasDataSource: CuestionarioPreguntasDataSource) : CuestionarioPreguntasRepository{
    override suspend fun getAllCuestionarioPreguntas(): CuestionarioPreguntasList {
        return cuestionarioPreguntasDataSource.getAllCuestionarioPreguntas()
    }

    override suspend fun getCuestionarioPreguntabyId(id: Long): CuestionarioPreguntasEntity {
        return cuestionarioPreguntasDataSource.getCuestionarioPreguntabyId(id)
    }

    override suspend fun saveCuestionarioPregunta(cuestionarioPregunta: CuestionarioPreguntasEntity) {
        return cuestionarioPreguntasDataSource.saveCuestionarioPregunta(cuestionarioPregunta)
    }

    override suspend fun saveCuestionarioWithCuestionarioPregunta(cuestionario: CuestionarioEntity): Long {
        return cuestionarioPreguntasDataSource.saveCuestionarioWithCuestionarioPregunta(cuestionario)
    }
}