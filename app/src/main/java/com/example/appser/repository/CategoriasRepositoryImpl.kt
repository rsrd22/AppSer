package com.example.appser.repository

import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.CategoriasList
import com.example.appser.data.resource.CategoriasDataSource

class CategoriasRepositoryImpl(private val categoriasDataSource: CategoriasDataSource): CategoriasRepository {
    override suspend fun getAllCategorias(): CategoriasList {
        return categoriasDataSource.getAllCategorias()
    }

    override suspend fun getCategoriaById(id: Long): CategoriasEntity {
        return categoriasDataSource.getCategoriabyId(id)
    }

    override suspend fun saveCategoria(categoria: CategoriasEntity) {
        return categoriasDataSource.saveCategoria(categoria)
    }
}