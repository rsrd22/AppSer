package com.example.appser.data.resource

import com.example.appser.data.local.CuestionarioPreguntasDao
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.CuestionarioPreguntasList

class CuestionarioPreguntasDataSource(private val cuestionariopreguntasDao: CuestionarioPreguntasDao) {

    suspend fun getAllCuestionarioPreguntas(): CuestionarioPreguntasList{
        return CuestionarioPreguntasList(cuestionariopreguntasDao.getAllCuestionarioPreguntas())
    }

    suspend fun getCuestionarioPreguntabyId(id: Long): CuestionarioPreguntasEntity{
        return cuestionariopreguntasDao.getCuestionarioPreguntasbyId(id)
    }

    suspend fun saveCuestionarioPregunta(cuestionariopreguntas: CuestionarioPreguntasEntity){
        return cuestionariopreguntasDao.saveCuestionarioPreguntas(cuestionariopreguntas)
    }

}