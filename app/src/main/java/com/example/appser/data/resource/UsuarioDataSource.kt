package com.example.appser.data.resource

import com.example.appser.data.local.UsuarioDao
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.model.UsuarioList

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
    suspend fun updateUsuario(usuario: UsuarioEntity){
        usuarioDao.updateUsuario(usuario)
    }
}