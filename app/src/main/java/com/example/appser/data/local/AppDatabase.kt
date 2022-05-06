package com.example.appser.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appser.data.model.*

@Database
    (entities = [PersonaEntity::class, UsuarioEntity::class, RolEntity::class, CicloVitalEntity::class,
    ActividadesEntity::class, CategoriasEntity::class, CuestionarioEntity::class,
    CuestionarioPreguntasEntity::class, EmocionesEntity::class, PreguntasEntity::class],
    version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun actividadesDao(): ActividadesDao
    abstract fun categoriasDao(): CategoriasDao
    abstract fun ciclovitalDao(): CicloVitalDao
    abstract fun cuestionarioDao(): CuestionarioDao
    abstract fun cuestionariopreguntasDao(): CuestionarioPreguntasDao
    abstract fun emocionesDao(): EmocionesDao
    abstract fun personaDao(): PersonaDao
    abstract fun preguntasDao(): PreguntasDao
    abstract fun rolDao(): RolDao
    abstract fun usuarioDao(): UsuarioDao


    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            INSTANCE= INSTANCE?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "application_ser_db"
            ).build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}