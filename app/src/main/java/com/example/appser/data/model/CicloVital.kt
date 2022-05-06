package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CicloVitalEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val descripcion: String = "",
    @ColumnInfo(name = "edad_inicial")
    val edadInicial: Int = -1,
    @ColumnInfo(name = "edad_final")
    val edadFinal: Int = -1,
    val estado: Int = -1,
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""
)

data class CicloVitalList(val result: List<CicloVitalEntity> = listOf())