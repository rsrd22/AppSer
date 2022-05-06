package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioPreguntasEntity

data class CuestionarioWithCuestionarioPreguntas(
    @Embedded val cuestionario: CuestionarioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn =  "id_cuestionario"
    )
    val cuestionarioPreguntas: List<CuestionarioPreguntasEntity>
)
