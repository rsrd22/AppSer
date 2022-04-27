package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.RolEntity

@Dao
interface RolDao {
    @Query("SELECT * FROM RolEntity")
    suspend fun getAllRol(): List<RolEntity>

    @Query("SELECT * FROM RolEntity WHERE id = :id")
    suspend fun getRol(id: Int):RolEntity

    @Insert
    suspend fun saveRol(rolEntity: RolEntity)

    @Update
    suspend fun updateRol(rolEntity: RolEntity)

}