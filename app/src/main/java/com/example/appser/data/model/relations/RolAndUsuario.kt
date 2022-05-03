package com.example.appser.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.UsuarioEntity

data class RolAndUsuario(
    @Embedded val rol: RolEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_rol"
    )
    val usuarios: List<UsuarioEntity>
)