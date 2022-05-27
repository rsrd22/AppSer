package com.example.appser.repository

import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.ActividadesList

interface ActividadesRepository {

    suspend fun getAllActividades(): ActividadesList

    suspend fun getActividadbyId(id: Long): ActividadesEntity

    suspend fun getActividadByEmocionByCiclo(idEmocion: Long, idCiclo: Long): List<ActividadesEntity>

    suspend fun saveActividad(actividadesEntity: ActividadesEntity)

}