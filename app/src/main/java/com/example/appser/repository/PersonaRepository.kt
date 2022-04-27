package com.example.appser.repository

import com.example.appser.data.model.PersonaList

interface PersonaRepository {

    suspend fun getPersona():PersonaList

}