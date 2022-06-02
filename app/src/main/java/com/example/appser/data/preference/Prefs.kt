package com.example.appser.data.preference

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "mydtb"
    val SHARED_ID_USER = "iduser"
    val SHARED_NAME_USER = "username"
    val SHARED_EMAIL_USER = "useremail"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveName(name: String){
        storage.edit().putString(SHARED_NAME_USER, name).apply()
    }

    fun saveIdUser(id: Long){
        storage.edit().putLong(SHARED_ID_USER, id).apply()
    }
    fun saveEmail(email: String){
        storage.edit().putString(SHARED_EMAIL_USER, email).apply()
    }

    fun getName(): String{
        return storage.getString(SHARED_NAME_USER, "")!!
    }

    fun getIdUser(): Long{
        return storage.getLong(SHARED_ID_USER, 0)
    }
    fun getEmail(): String{
        return storage.getString(SHARED_EMAIL_USER, "")!!
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

}