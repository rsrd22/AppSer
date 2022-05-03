package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.CicloVitalEntity

@Dao
interface CicloVitalDao {
    @Query("SELECT * FROM CicloVitalEntity")
    suspend fun getAllCicloVital(): List<CicloVitalEntity>

    @Query("SELECT * FROM CicloVitalEntity WHERE id = :id")
    suspend fun getCicloVitalbyId(id: Long): CicloVitalEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCicloVital(ciclovitalEntity: CicloVitalEntity)
}