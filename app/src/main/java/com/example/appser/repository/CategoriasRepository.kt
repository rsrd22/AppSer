package com.example.appser.repository

import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.CategoriasList


interface CategoriasRepository {
    suspend fun getAllCategorias(): CategoriasList

    suspend fun getCategoriaById(id: Long): CategoriasEntity

    suspend fun saveCategoria(categoria: CategoriasEntity)
}