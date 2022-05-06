package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.model.relations.CicloVitalWithActividades
import com.example.appser.data.model.relations.EmocionesWithActividades
import com.example.appser.data.model.relations.EmocionesWithPreguntas

@Dao
interface EmocionesDao {
    @Transaction
    @Query("SELECt * FROM EmocionesEntity")
    suspend fun getAllEmociones(): EmocionesList

    @Transaction
    @Query("SELECt * FROM EmocionesEntity WHERE id = :id")
    suspend fun getEmocionbyId(id: Long): EmocionesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmocion(emocion: EmocionesEntity)

    @Transaction
    @Query("SELECT * FROM EmocionesEntity WHERE id = :id")
    suspend fun getEmocionesWithActividades(id: Long): List<EmocionesWithActividades>

    @Transaction
    @Query("SELECT * FROM EmocionesEntity WHERE id = :id")
    suspend fun getEmocionesWithPreguntas(id: Long): List<EmocionesWithPreguntas>

}