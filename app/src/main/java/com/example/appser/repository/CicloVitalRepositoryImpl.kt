package com.example.appser.repository

import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.CicloVitalList
import com.example.appser.data.resource.CicloVitalDataResource

class CicloVitalRepositoryImpl(private val cicloDataSource: CicloVitalDataResource): CicloVitalRepository {
    override suspend fun getAllCicloVital(): CicloVitalList {
        return cicloDataSource.getAllCicloVital()
    }

    override suspend fun getCicloVitalById(id: Long): CicloVitalEntity {
        return cicloDataSource.getCicloVitalbyId(id)
    }

    override suspend fun saveCicloVital(ciclovitalEntity: CicloVitalEntity) {
        return cicloDataSource.saveCicloVital(ciclovitalEntity)
    }
}