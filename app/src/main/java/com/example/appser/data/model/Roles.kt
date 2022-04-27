package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rol(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    @ColumnInfo(name = "descripcion")
    val descripcion: String = "",
    @ColumnInfo(name = "estado")
    val estado: Int = -1,
    @ColumnInfo(name = "user_create")
    val user_create: String = "",
    @ColumnInfo(name = "create_at")
    val create_at: String = ""
)

data class RolList (val results: List<Rol> = listOf() )