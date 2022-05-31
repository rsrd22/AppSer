package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.relations.PersonaWithCuestionario

@Dao
interface PersonaDao {

    @Transaction
    @Query("SELECT * FROM PersonaEntity")
    suspend fun getAllPersona(): List<PersonaEntity>

    @Transaction
    @Query("SELECT * FROM PersonaEntity WHERE id = :id")
    suspend fun getPersona(id: Int): PersonaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePersona(personaEntity: PersonaEntity)

    @Transaction
    @Query("SELECT * FROM PersonaEntity WHERE id = :id")
    suspend fun getPersonaWithCuestionario(id: Long): PersonaWithCuestionario



}