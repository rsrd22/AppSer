package com.example.appser.repository

import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.CategoriasList
import com.example.appser.data.model.relations.CategoriasWithPreguntas


interface CategoriasRepository {
    suspend fun getAllCategorias(): CategoriasList

    suspend fun getCategoriaById(id: Long): CategoriasEntity

    suspend fun saveCategoria(categoria: CategoriasEntity)

    suspend fun getAllCategoriaWithPreguntas(): List<CategoriasWithPreguntas>

    suspend fun getCategoriaWithPreguntasById(id: Long): CategoriasWithPreguntas
}