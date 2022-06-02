package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class CuestionarioEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_persona")
    val personaId: Long = 0,
    @ColumnInfo(name = "id_actividad_asignada")
    val actividadAsignadaId: Long = 0,
    @ColumnInfo(name = "fecha")
    val fecha: String = "",
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""


){
    @Ignore
    var listaCuestionarioPreguntas: List<CuestionarioPreguntasEntity>? = mutableListOf()
}


data class HistoricoCuestionario(
    @ColumnInfo(name = "fecha") val fecha: String?,
    @ColumnInfo(name = "titulo") val actividad: String?,
    @ColumnInfo(name = "nombre") val emocion: String?
)

data class CuestionarioList (val results: List<CuestionarioEntity> = listOf() )