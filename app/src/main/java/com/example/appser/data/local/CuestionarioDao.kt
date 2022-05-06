package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioList

@Dao
interface CuestionarioDao {

    @Query("SELECT * FROM CuestionarioEntity")
    suspend fun getAllCuestionario(): CuestionarioList

    @Query("SELECT * FROM CuestionarioEntity WHERE id = :id")
    suspend fun getCuestionariobyId(id: Long): CuestionarioEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  saveCuestionario(cuestionario: CuestionarioEntity)

}