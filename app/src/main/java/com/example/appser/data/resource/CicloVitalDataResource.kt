package com.example.appser.data.resource

import com.example.appser.data.local.CicloVitalDao
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.CicloVitalList

class CicloVitalDataResource(private val ciclovitalDao: CicloVitalDao) {
    suspend fun getAllCicloVital(): CicloVitalList{
        return CicloVitalList(ciclovitalDao.getAllCicloVital())
    }

    suspend fun getCicloVitalbyId(id: Long): CicloVitalEntity{
        return ciclovitalDao.getCicloVitalbyId(id)
    }

    suspend fun saveCicloVital(cicloVital: CicloVitalEntity){
        return ciclovitalDao.saveCicloVital(cicloVital)
    }

    suspend fun getCicloVitalByEdad(edad: Int): CicloVitalEntity{
        return ciclovitalDao.getCicloVitalByEdad(edad)
    }
}