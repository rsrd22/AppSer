package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmocionesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val descripcion: String = "",
    val estado: Int = -1,
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""
)

data class EmocionesList(val result: List<EmocionesEntity> = listOf())