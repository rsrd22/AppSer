package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.CategoriasEntity

@Dao
interface CategoriasDao {
    @Query("SELECT * FROM CategoriasEntity")
    suspend fun getAllCategorias(): List<CategoriasEntity>

    @Query("SELECT * FROM CicloVitalEntity WHERE id = :id")
    suspend fun getCategoriabyId(id: Long): CategoriasEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategoria(categoriaEntity: CategoriasEntity)
}