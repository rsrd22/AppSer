package com.example.appser.repository

import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.ActividadesList
import com.example.appser.data.resource.ActividadesDataSource

class ActividadesRepositoryImpl(private val actividadesDataSource: ActividadesDataSource): ActividadesRepository {
    override suspend fun getAllActividades(): ActividadesList {
        return actividadesDataSource.getAllActividades()
    }

    override suspend fun getActividadbyId(id: Long): ActividadesEntity {
        return actividadesDataSource.getActividadbyId(id)
    }

    override suspend fun saveActividad(actividadesEntity: ActividadesEntity) {
        return actividadesDataSource.saveActividad(actividadesEntity)
    }
}