package com.example.appser.data.resource

import com.example.appser.data.local.CuestionarioDao
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.CuestionarioList

class CuestionarioDataSource(private val cuestionarioDao: CuestionarioDao) {

    suspend fun getAllCuestionario(): CuestionarioList{
        return CuestionarioList(cuestionarioDao.getAllCuestionario())
    }

    suspend fun getCuestionariobyId(id: Long): CuestionarioEntity{
        return cuestionarioDao.getCuestionariobyId(id)
    }

    suspend fun saveCuestionario(cuestionario: CuestionarioEntity){
        return cuestionarioDao.saveCuestionario(cuestionario)
    }
}