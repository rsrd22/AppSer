package com.example.appser.repository


import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.RolList

interface RolRepository {

    suspend fun getRoles(): RolList

    suspend fun getRolById(id: Long): RolEntity

    suspend fun saveRol(rolEntity: RolEntity)
}