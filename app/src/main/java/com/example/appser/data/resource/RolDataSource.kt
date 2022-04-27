package com.example.appser.data.resource

import com.example.appser.data.local.RolDao
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.RolList

class RolDataSource(private val rolDao: RolDao) {
    suspend fun  getAllRol(): RolList {
        return RolList(rolDao.getAllRol())
    }
    suspend fun getRol(id: Int): RolEntity {
        return rolDao.getRol(id)
    }

    suspend fun savePersona(rol: RolEntity){
        rolDao.saveRol(rol)
    }
    suspend fun updatePersona(rol: RolEntity){
        rolDao.updateRol(rol)
    }

}