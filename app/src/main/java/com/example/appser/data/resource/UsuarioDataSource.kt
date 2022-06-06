package com.example.appser.data.resource

import android.util.Log
import com.example.appser.data.local.UsuarioDao
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.UsuarioList
import com.example.appser.data.model.relations.PersonaAndUsuario

class UsuarioDataSource(private val usuarioDao: UsuarioDao) {

    suspend fun  getAllUsuario(): UsuarioList{
        return UsuarioList(usuarioDao.getAllUsuario())
    }
    suspend fun  getUsuario(id: Int): UsuarioEntity{
        return usuarioDao.getUsuario(id)
    }

    suspend fun saveUsuario(usuario: UsuarioEntity){
        usuarioDao.saveUsuario(usuario)
    }

    suspend fun savePersona(personaEntity: PersonaEntity){
        usuarioDao.savePersona(personaEntity)
    }

    suspend fun getPersonasAndUsuario(): List<PersonaAndUsuario>{
        return usuarioDao.getPersonasAndUsuario()
    }

    suspend fun insertPersonaWithUsuario(persona: PersonaEntity){
        usuarioDao.insertPersonaWithUsuario(persona)
    }

    suspend fun getUsuarioByEmail(email: String): PersonaAndUsuario {
        Log.d("source", "email: ${email}")
        return usuarioDao.getUsuarioByEmail(email)
    }

}