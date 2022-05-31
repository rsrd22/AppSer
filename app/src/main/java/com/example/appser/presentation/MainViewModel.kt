package com.example.appser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.model.relations.CategoriasWithPreguntas
import com.example.appser.data.model.relations.PersonaAndUsuario

class MainViewModel: ViewModel() {
    private val personaUsuario= MutableLiveData<PersonaAndUsuario>()
    private val emociones = MutableLiveData<EmocionesList>()
    private val ciclovital = MutableLiveData<CicloVitalEntity>()
    private val actividadAsignada = MutableLiveData<ActividadesEntity>()
    private val categorias= MutableLiveData<List<CategoriasWithPreguntas>>()
    private val emocioncuestionarioId = MutableLiveData<Pair<Long, Long>>()

    fun setPersonaAndUsuario(_personaAndUsuario: PersonaAndUsuario){
        personaUsuario.value = _personaAndUsuario
    }

    fun getPersonaAndUsuario(): LiveData<PersonaAndUsuario> {
        return personaUsuario
    }

    fun getEmociones(): LiveData<EmocionesList>{
        return emociones
    }

    fun setEmociones(_emociones: EmocionesList){
        emociones.value = _emociones
    }

    fun getCategoriasWithPreguntas(): LiveData<List<CategoriasWithPreguntas>> {
        return categorias
    }

    fun setCategoriasWithPreguntas(_categorias: List<CategoriasWithPreguntas>){
        categorias.value = _categorias
    }

    fun getDatos(): Pair<MutableLiveData<List<CategoriasWithPreguntas>>, MutableLiveData<EmocionesList>> {
        return Pair(categorias, emociones)
    }

    fun getEmocionCuestionarioId(): LiveData<Pair<Long, Long>>{
        return emocioncuestionarioId
    }

    fun setEmocionCuestionarioId(datos: Pair<Long, Long>){
        emocioncuestionarioId.value = datos
    }

    fun getCicloVital(): LiveData<CicloVitalEntity>{
        return ciclovital
    }

    fun setCicloVital(_ciclovital: CicloVitalEntity){
        ciclovital.value = _ciclovital
    }

    fun getActividadAsignada(): LiveData<ActividadesEntity>{
        return actividadAsignada
    }

    fun setActividadAsignada(_actividadAsignada: ActividadesEntity){
        actividadAsignada.value = _actividadAsignada
    }

}