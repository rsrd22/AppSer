package com.example.appser.repository

import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.UsuarioList

interface UsuarioRepository {

    suspend fun getUsuarios(): UsuarioList

    suspend fun getUsuarioById(id: Int): UsuarioEntity

    suspend fun saveUsuario(usuarioEntity: UsuarioEntity)

    suspend fun insertPersonaWithUsuario(persona: PersonaEntity)


}