package com.example.appser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.RolEntity
import com.example.appser.data.model.UsuarioEntity

@Database(entities = [PersonaEntity::class, UsuarioEntity::class, RolEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){


    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            INSTANCE= INSTANCE?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_ser"
            ).build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}