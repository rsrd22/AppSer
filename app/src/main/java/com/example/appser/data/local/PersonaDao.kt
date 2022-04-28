package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity

@Dao
interface PersonaDao {

    @Query("SELECT * FROM PersonaEntity")
    suspend fun getAllPersona(): List<PersonaEntity>

    @Query("SELECT * FROM PersonaEntity WHERE id = :id")
    suspend fun getPersona(id: Int): PersonaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePersona(personaEntity: PersonaEntity)



}