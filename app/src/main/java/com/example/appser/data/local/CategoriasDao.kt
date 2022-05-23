package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.relations.CategoriasWithPreguntas
@Dao
interface CategoriasDao {

    @Transaction
    @Query("SELECT * FROM CategoriasEntity")
    suspend fun getAllCategorias(): List<CategoriasEntity>

    @Transaction
    @Query("SELECT * FROM CicloVitalEntity WHERE id = :id")
    suspend fun getCategoriabyId(id: Long): CategoriasEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategoria(categoriaEntity: CategoriasEntity)


    @Transaction
    @Query("SELECT * FROM CategoriasEntity")
    suspend fun getAllCategoriasWithPreguntas(): List<CategoriasWithPreguntas>

    @Transaction
    @Query("SELECT * FROM CategoriasEntity WHERE id = :id")
    suspend fun getCategoriasWithPreguntasById(id: Long): CategoriasWithPreguntas

}