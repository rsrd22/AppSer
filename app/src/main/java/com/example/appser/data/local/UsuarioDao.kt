package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.relations.PersonaAndUsuario

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
    @Query("SELECT * FROM PersonaEntity")
    suspend fun getPersonasAndUsuario(): List<PersonaAndUsuario>


    suspend fun insertPersonaWithUsuario(persona: PersonaEntity) {
        var usuario: UsuarioEntity ?= persona.usuario
        if (usuario!= null) {
            val id_persona: Long = savePersona(persona)
            usuario.personaId = id_persona
            saveUsuario(usuario)

        }

    }

    @Transaction
    @Query("SELECT * FROM UsuarioEntity, personaentity WHERE email= :email")
    suspend fun getUsuarioByEmail(email: String): PersonaAndUsuario

    @Transaction
    @Query("SELECT * FROM UsuarioEntity, personaentity WHERE id_persona= :id")
    suspend fun getUsuarioByIdPersona(id: Long): PersonaAndUsuario


}