package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList

@Dao
interface EmocionesDao {
    @Query("SELECt * FROM EmocionesEntity")
    suspend fun getAllEmociones(): EmocionesList

    @Query("SELECt * FROM EmocionesEntity WHERE id = :id")
    suspend fun getEmocionbyId(id: Long): EmocionesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmocion(emocion: EmocionesEntity)
}