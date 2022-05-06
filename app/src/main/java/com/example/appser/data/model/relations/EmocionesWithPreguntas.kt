package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.PreguntasEntity

data class EmocionesWithPreguntas(
    @Embedded val emocion: EmocionesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_emocion"
    )
    val preguntas: List<PreguntasEntity>
)
