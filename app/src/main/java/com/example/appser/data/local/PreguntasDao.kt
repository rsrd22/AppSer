package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.PreguntasEntity
import com.example.appser.data.model.PreguntasList

@Dao
interface PreguntasDao {
    @Query("SELECT * FROM PreguntasEntity")
    suspend fun getAllPreguntas(): List<PreguntasEntity>

    @Query("SELECT * FROM PreguntasEntity WHERE id = :id")
    suspend fun getPreguntabyId(id: Long): PreguntasEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  savePregunta(preguntasEntity: PreguntasEntity)
}