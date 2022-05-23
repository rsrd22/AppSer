package com.example.appser.data.resource

import com.example.appser.data.local.CategoriasDao
import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.CategoriasList
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.CicloVitalList
import com.example.appser.data.model.relations.CategoriasWithPreguntas

class CategoriasDataSource(private val categoriasDao: CategoriasDao) {
    suspend fun getAllCategorias(): CategoriasList {
        return CategoriasList(categoriasDao.getAllCategorias())
    }

    suspend fun getCategoriabyId(id: Long): CategoriasEntity {
        return categoriasDao.getCategoriabyId(id)
    }

    suspend fun saveCategoria(categoria: CategoriasEntity){
        return categoriasDao.saveCategoria(categoria)
    }

    suspend fun getAllCategoriasWithPreguntas(): List<CategoriasWithPreguntas> {
        return categoriasDao.getAllCategoriasWithPreguntas()
    }

    suspend fun getCategoriasWithPreguntasById(id: Long): CategoriasWithPreguntas{
        return categoriasDao.getCategoriasWithPreguntasById(id)
    }

}