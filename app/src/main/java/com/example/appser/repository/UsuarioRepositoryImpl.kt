package com.example.appser.repository

import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.UsuarioList
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.resource.UsuarioDataSource

class UsuarioRepositoryImpl(private val usuarioDataSource: UsuarioDataSource): UsuarioRepository{
    override suspend fun getUsuarios(): UsuarioList {
        return usuarioDataSource.getAllUsuario()
    }

    override suspend fun getUsuarioById(id: Int): UsuarioEntity {
        return usuarioDataSource.getUsuario(id)
    }

    override suspend fun saveUsuario(usuarioEntity: UsuarioEntity){
        return usuarioDataSource.saveUsuario(usuarioEntity)
    }

    override suspend fun getPersonasAndUsuario(): List<PersonaAndUsuario> {
        return usuarioDataSource.getPersonasAndUsuario()
    }

    override suspend fun insertPersonaWithUsuario(persona: PersonaEntity) {
        return usuarioDataSource.insertPersonaWithUsuario(persona)
    }

    override suspend fun getUsuarioByEmail(email: String): PersonaAndUsuario {
        return usuarioDataSource.getUsuarioByEmail(email)
    }
}