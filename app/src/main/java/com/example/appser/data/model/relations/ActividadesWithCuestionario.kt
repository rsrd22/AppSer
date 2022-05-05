package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.CuestionarioEntity

data class ActividadesWithCuestionario (
    @Embedded val actividad: ActividadesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_actividad_asignada"
    )
    val cuestionario: List<CuestionarioEntity>
)