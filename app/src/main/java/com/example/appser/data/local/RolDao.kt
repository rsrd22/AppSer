package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.model.relations.RolAndUsuario

@Dao
interface RolDao {
    @Query("SELECT * FROM RolEntity")
    suspend fun getAllRol(): List<RolEntity>

    @Query("SELECT * FROM RolEntity WHERE id = :id")
    suspend fun getRol(id: Long):RolEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRol(rolEntity: RolEntity)

    @Transaction
    @Query("SELECT * FROM RolEntity WHERE id = :idRol")
    suspend fun getRolAndUsuario(idRol: Long): RolAndUsuario


}