package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.PreguntasEntity

data class CategoriasWithPreguntas(
    @Embedded val categoria: CategoriasEntity,
    @Relation(
        parentColumn = "id",
        entityColumn =  "id_categoria"
    )
    val preguntas: List<PreguntasEntity>
)
