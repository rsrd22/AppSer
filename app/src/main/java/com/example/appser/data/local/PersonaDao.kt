package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity

@Dao
interface PersonaDao {

    @Query("SELECT * FROM PersonaEntity")
    suspend fun getAllPersona(): List<PersonaEntity>

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id")
    suspend fun getPersona(id: Int): PersonaEntity

    @Insert
    suspend fun savePersona(personaEntity: PersonaEntity)

    @Update
    suspend fun updatePersona(personaEntity: PersonaEntity)

}