package com.example.appser.repository

import com.example.appser.data.local.EmocionesDao
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList

interface EmocionesRepository {

    suspend fun getAllEmociones(): EmocionesList

    suspend fun getEmocionbyId(id: Long): EmocionesEntity

    suspend fun saveEmocion(emocionesEntity: EmocionesEntity)

}