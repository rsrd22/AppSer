package com.example.appser.data.resource

import com.example.appser.data.local.ActividadesDao
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.ActividadesList

class ActividadesDataSource(private val actividadesDao: ActividadesDao) {
    suspend fun getAllActividades(): ActividadesList {
        return ActividadesList(actividadesDao.getAllActividades())
    }

    suspend fun getActividadbyId(id: Long): ActividadesEntity {
        return actividadesDao.getActividadbyId(id)
    }

    suspend fun saveActividad(actividad: ActividadesEntity) {
        return actividadesDao.saveActividad(actividad)
    }
}