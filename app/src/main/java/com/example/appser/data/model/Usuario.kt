package com.example.appser.data.model

import android.app.Person
import androidx.room.*

@Entity
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "estado")
    val estado: Int = -1,
    @ColumnInfo(name = "id_persona")
    var personaId:Long  = 0,
    @ColumnInfo(name = "id_rol")
    val rolId: Long = 0,
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""
)

data class PersonaAndUsuario(
    @Embedded val persona: PersonaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_persona"
    )
    val usuario: UsuarioEntity
)

data class RolAndUsuario(
    @Embedded val rol: RolEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "rolId"
    )
    val usuarios: List<UsuarioEntity>
)

data class UsuarioList(val results: List<UsuarioEntity> = listOf())