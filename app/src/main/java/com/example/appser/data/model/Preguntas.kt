package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreguntasEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name="descripcion")
    val descripcion: String = "",
    @ColumnInfo(name="id_categoria")
    val categoriaId: Long = 0,
    @ColumnInfo(name="id_emocion")
    val emocionId: Long = 0,
    @ColumnInfo(name="estado")
    val estado: Int = -1,
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""

)

data class PreguntasList(val result: List<PreguntasEntity> = listOf())
