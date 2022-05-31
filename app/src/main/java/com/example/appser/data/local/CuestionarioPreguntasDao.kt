package com.example.appser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioPreguntasEntity
import com.example.appser.data.model.CuestionarioPreguntasList
import com.example.appser.data.model.UsuarioEntity

@Dao
interface CuestionarioPreguntasDao {

    @Query("SELECT * FROM CuestionarioPreguntasEntity")
    suspend fun getAllCuestionarioPreguntas(): List<CuestionarioPreguntasEntity>

    @Query("SELECT * FROM CuestionarioPreguntasEntity WHERE id = :id")
    suspend fun getCuestionarioPreguntasbyId(id: Long): CuestionarioPreguntasEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCuestionarioPreguntas(cuestionarioPreguntas: CuestionarioPreguntasEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  saveCuestionario(cuestionario: CuestionarioEntity): Long

    suspend fun saveCuestionarioWithCuestionarioPregunta(cuestionario: CuestionarioEntity): Long{
        var cuestionariopreguntas: List<CuestionarioPreguntasEntity> ?= cuestionario.listaCuestionarioPreguntas
        var id_cuestionario: Long = 0
        if (cuestionariopreguntas!= null) {
            id_cuestionario = saveCuestionario(cuestionario)
            for(cuestionatioPreguntasItem in cuestionariopreguntas){
                cuestionatioPreguntasItem.cuestionarioId = id_cuestionario
                saveCuestionarioPreguntas(cuestionatioPreguntasItem)
            }

        }
        return id_cuestionario
    }

}