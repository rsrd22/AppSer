package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActividadesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name="titulo")
    val titulo: String ?= "",
    @ColumnInfo(name="descripcion")
    val descripcion: String ?= "",
    @ColumnInfo(name="enlace")
    val enlace: String ?= "",
    @ColumnInfo(name="id_emocion")
    val emocionId: Long ?= 0,
    @ColumnInfo(name="id_ciclo")
    val cicloId: Long ?= 0,
    @ColumnInfo(name="estado")
    val estado: Int ?= -1,
    @ColumnInfo(name = "user_create")
    val user_create: String ?= "",
    @ColumnInfo(name = "create_at")
    val create_at: String ?= ""
)

data class ActividadesList(val result: List<ActividadesEntity> = listOf())