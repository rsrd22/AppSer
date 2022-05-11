package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.ActividadesList
import com.example.appser.data.model.relations.ActividadesWithCuestionario
import com.example.appser.data.model.relations.PersonaWithCuestionario

@Dao
interface ActividadesDao {
    @Transaction
    @Query("SELECT * FROM ActividadesEntity")
    suspend fun getAllActividades(): List<ActividadesEntity>

    @Transaction
    @Query("SELECT * FROM ActividadesEntity WHERE id = :id")
    suspend fun getActividadbyId(id: Long): ActividadesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActividad(actividad: ActividadesEntity)

    @Transaction
    @Query("SELECT * FROM ActividadesEntity WHERE id = :id")
    suspend fun getActividadesWithCuestionario(id: Long): List<ActividadesWithCuestionario>
}