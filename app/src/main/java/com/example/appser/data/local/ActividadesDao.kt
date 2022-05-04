package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.ActividadesList

@Dao
interface ActividadesDao {

    @Query("SELECT * FROM ActividadesEntity")
    suspend fun getAllActividades(): ActividadesList

    @Query("SELECT * FROM ActividadesEntity WHERE id = :id")
    suspend fun getActividadbyId(id: Long): ActividadesEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActividad(actividad: ActividadesEntity)
}