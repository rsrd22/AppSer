package com.example.appser.data.preference

import android.app.Application

class SerApplication: Application() {
    companion object{
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}