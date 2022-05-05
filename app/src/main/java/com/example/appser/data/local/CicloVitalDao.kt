package com.example.appser.data.local

import androidx.room.*
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.relations.ActividadesWithCuestionario
import com.example.appser.data.model.relations.CicloVitalWithActividades

@Dao
interface CicloVitalDao {
    @Transaction
    @Query("SELECT * FROM CicloVitalEntity")
    suspend fun getAllCicloVital(): List<CicloVitalEntity>

    @Transaction
    @Query("SELECT * FROM CicloVitalEntity WHERE id = :id")
    suspend fun getCicloVitalbyId(id: Long): CicloVitalEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCicloVital(ciclovitalEntity: CicloVitalEntity)

    @Transaction
    @Query("SELECT * FROM CicloVitalEntity WHERE id = :id")
    suspend fun getCicloVitalWithActividades(id: Long): List<CicloVitalWithActividades>


}