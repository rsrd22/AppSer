package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CuestionarioPreguntasEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_cuestionario")
    val cuestionarioId: Long = 0,
    @ColumnInfo(name = "id_pregunta")
    val preguntaId: Long = 0,
    @ColumnInfo(name = "respuesta")
    val respuesta: String = "",
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""
)

data class CuestionarioPreguntasList (val results: List<CuestionarioPreguntasEntity> = listOf() )

