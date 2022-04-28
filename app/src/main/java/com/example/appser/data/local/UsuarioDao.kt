package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM UsuarioEntity")
    suspend fun getAllUsuario(): List<UsuarioEntity>

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id")
    suspend fun getUsuario(id: Int): UsuarioEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsuario(usuarioEntity: UsuarioEntity)


}