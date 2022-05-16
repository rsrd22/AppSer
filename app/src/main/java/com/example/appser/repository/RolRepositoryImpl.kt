package com.example.appser.repository

import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.RolList
import com.example.appser.data.resource.RolDataSource

class RolRepositoryImpl(private val rolDataSource: RolDataSource): RolRepository {
    override suspend fun getRoles(): RolList {
        return rolDataSource.getAllRol()
    }

    override suspend fun getRolById(id: Long): RolEntity {
        return rolDataSource.getRol(id)
    }

    override suspend fun saveRol(rolEntity: RolEntity) {
        return rolDataSource.saveRol(rolEntity)
    }

    override suspend fun getCountRol(): Int {
        return rolDataSource.getCountRol()
    }
}