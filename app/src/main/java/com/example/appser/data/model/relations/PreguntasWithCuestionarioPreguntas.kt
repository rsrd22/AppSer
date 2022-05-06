package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.PreguntasEntity

data class PreguntasWithCuestionarioPreguntas(
    @Embedded val pregunta: PreguntasEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_pregunta"
    )
    val cuestionarioPreguntas: CuestionarioPreguntasEntity
)
