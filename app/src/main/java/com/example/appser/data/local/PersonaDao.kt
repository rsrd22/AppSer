package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.HistoricoCuestionario
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

    @Transaction
    @Query("SELECT cues.fecha, act.titulo, emo.nombre  FROM cuestionarioentity AS cues" +
            "    LEFT JOIN actividadesentity AS act ON act.id =  cues.id_actividad_asignada" +
            "    LEFT JOIN emocionesentity AS emo ON emo.id = act.id_emocion" +
            "    WHERE cues.id_persona = :id")
    suspend fun getHistoricoCuestionario(id: Long): List<HistoricoCuestionario>



}