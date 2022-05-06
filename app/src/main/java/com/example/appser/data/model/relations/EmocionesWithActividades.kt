package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.EmocionesEntity

data class EmocionesWithActividades(
    @Embedded val emocion: EmocionesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_emocion"
    )
    val actividades: List<ActividadesEntity>
)
