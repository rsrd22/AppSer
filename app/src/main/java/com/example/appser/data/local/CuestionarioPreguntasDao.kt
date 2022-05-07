package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.CuestionarioPreguntasList

@Dao
interface CuestionarioPreguntasDao {

    @Query("SELECT * FROM CuestionarioPreguntasEntity")
    suspend fun getAllCuestionarioPreguntas(): List<CuestionarioPreguntasEntity>

    @Query("SELECT * FROM CuestionarioPreguntasEntity WHERE id = :id")
    suspend fun getCuestionarioPreguntasbyId(id: Long): CuestionarioPreguntasEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCuestionarioPreguntas(cuestionarioPreguntas: CuestionarioPreguntasEntity)

}