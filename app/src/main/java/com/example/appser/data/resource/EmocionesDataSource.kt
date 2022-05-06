package com.example.appser.data.resource

import com.example.appser.data.local.EmocionesDao
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList

class EmocionesDataSource(private val emocionesDao: EmocionesDao) {
    suspend fun getAllEmociones(): EmocionesList{
        return emocionesDao.getAllEmociones()
    }

    suspend fun getEmocionbyId(id: Long): EmocionesEntity{
        return emocionesDao.getEmocionbyId(id)
    }

    suspend fun saveEmocion(emocion: EmocionesEntity){
        return emocionesDao.saveEmocion(emocion)
    }
}