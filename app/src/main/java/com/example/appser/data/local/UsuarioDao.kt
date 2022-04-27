package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM UsuarioEntity")
    suspend fun getAllUsuario(): List<UsuarioEntity>

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id")
    suspend fun getUsuario(id: Int): UsuarioEntity

    @Insert
    suspend fun saveUsuario(usuarioEntity: UsuarioEntity)

    @Update
    suspend fun updateUsuario(usuarioEntity: UsuarioEntity)

}