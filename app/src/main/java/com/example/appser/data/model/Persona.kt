package com.example.appser.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "nombre_completo")
    val nombre_completo: String ?= "",
    @ColumnInfo(name = "edad")
    val edad: Int ?=-1,
    @ColumnInfo(name = "genero")
    val genero: String ?= "",
    @ColumnInfo(name = "user_create")
    val user_create: String ?= "",
    @ColumnInfo(name = "create_at")
    val create_at: String ?= ""

)

data class PersonaList (val results: List<PersonaEntity> = listOf())

