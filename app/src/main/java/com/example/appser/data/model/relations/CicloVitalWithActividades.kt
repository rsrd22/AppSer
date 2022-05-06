package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.CicloVitalEntity

data class CicloVitalWithActividades (
    @Embedded val cicloVital: CicloVitalEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_ciclo"
    )
    val actividades : List<ActividadesEntity>
)
