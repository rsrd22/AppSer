package com.example.appser.repository

import com.example.appser.data.local.EmocionesDao
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.resource.EmocionesDataSource

class EmocionesRepositoryImpl(private val emocionesDataSource: EmocionesDataSource): EmocionesRepository {
    override suspend fun getAllEmociones(): EmocionesList {
        return emocionesDataSource.getAllEmociones()
    }

    override suspend fun getEmocionbyId(id: Long): EmocionesEntity {
        return emocionesDataSource.getEmocionbyId(id)
    }

    override suspend fun saveEmocion(emocionesEntity: EmocionesEntity) {
        return emocionesDataSource.saveEmocion(emocionesEntity)
    }
}