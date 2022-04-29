package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.PersonaAndUsuario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM UsuarioEntity")
    suspend fun getAllUsuario(): List<UsuarioEntity>

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id")
    suspend fun getUsuario(id: Int): UsuarioEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsuario(usuarioEntity: UsuarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePersona(personaEntity: PersonaEntity): Long

    @Transaction
    @Query("SELECT * FROM UsuarioEntity")
    suspend fun getPersonasAndUsuario(): List<PersonaAndUsuario>


    suspend fun insertPersonaWithUsuario(persona: PersonaEntity) {
        var usuario: UsuarioEntity ?= persona.usuario
        if (usuario!= null) {
            val id_persona: Long = savePersona(persona)
            usuario.personaId = id_persona
            saveUsuario(usuario)

        }

    }


}