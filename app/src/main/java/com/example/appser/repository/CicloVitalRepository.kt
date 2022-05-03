package com.example.appser.repository

import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.CicloVitalList
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.RolList

interface CicloVitalRepository {

    suspend fun getAllCicloVital(): CicloVitalList

    suspend fun getCicloVitalById(id: Long): CicloVitalEntity

    suspend fun saveCicloVital(ciclovitalEntity: CicloVitalEntity)
}