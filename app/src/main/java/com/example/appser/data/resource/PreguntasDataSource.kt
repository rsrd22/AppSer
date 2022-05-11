package com.example.appser.data.resource

import com.example.appser.data.local.PreguntasDao
import com.example.appser.data.model.PreguntasEntity
import com.example.appser.data.model.PreguntasList

class PreguntasDataSource(private val preguntasDao: PreguntasDao) {
    suspend fun getAllPreguntas():PreguntasList{
        return PreguntasList(preguntasDao.getAllPreguntas())
    }

    suspend fun getPreguntabyId(id: Long): PreguntasEntity{
        return preguntasDao.getPreguntabyId(id)
    }

    suspend fun savePregunta(preguntasEntity: PreguntasEntity){
        return preguntasDao.savePregunta(preguntasEntity)
    }

}