package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity

data class PersonaWithCuestionario (
    @Embedded
    val persona: PersonaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_persona"
    )
    val cuestionario: List<CuestionarioEntity>
)