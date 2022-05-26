package com.example.appser.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.appser.R
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.*
import com.example.appser.data.model.relations.CategoriasWithPreguntas
import com.example.appser.databinding.FragmentDashboardBinding
import com.example.appser.databinding.FragmentHomeBinding
import com.example.appser.databinding.FragmentQuestionsBinding
import com.example.appser.presentation.MainViewModel
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment(R.layout.fragment_questions) {

    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var appDatabase: AppDatabase
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var categorias: List<CategoriasWithPreguntas>
    private lateinit var emociones: EmocionesList
    private lateinit var listaPreguntas: List<PreguntasEntity>
    private lateinit var listaRespuestasPreguntas: MutableList<modeloPreguntas>
    private var indicadorCategoria: Int = -1
    private var indicadorPregunta: Int = -1
    private var indicadorEmocion: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDatabase = AppDatabase.getDatabase(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionsBinding.bind(view)
        indicadorCategoria = 0
        indicadorPregunta = 0
        listaRespuestasPreguntas = mutableListOf()
        Log.d("Question", "Main Question")

        mainViewModel.getCategoriasWithPreguntas().observe(viewLifecycleOwner, Observer{cat ->

            Log.d("Categorias-->", "cate->${cat}")
            categorias = cat

            mainViewModel.getEmociones().observe(viewLifecycleOwner, Observer { result ->
                Log.d("Emociones-->", "Emo->${result}")
                emociones = result

                if(categorias != null && emociones != null){
                    IniciarPreguntas()
                }
            })
        })


        btnSiguiente.setOnClickListener {
            setSiguentePregunta()
        }
    }

    fun setEmociones(){

    }

    fun setCategorias(){

    }

    fun IniciarPreguntas(){
        indicadorCategoria = 0
        indicadorEmocion  = emocionRandon()
        setListadoPreguntas()
        setPregunta()

    }

    fun setListadoPreguntas(){
        listaPreguntas = categorias[indicadorCategoria].preguntas.filter { it.emocionId == emociones.result[indicadorEmocion].id }
        indicadorPregunta = 0
    }


    fun AumentarCategoria(){
        if(indicadorCategoria < categorias.size)
            indicadorCategoria++
    }


    fun emocionRandon(): Int{
        val position = (0..3).random()
        return position
    }

    fun setPregunta(){
        binding.txtPreguntas.text = "${listaPreguntas[indicadorPregunta].descripcion}"
    }

    fun validarRespuesta(): Boolean{
        return !binding.rbNo.isSelected && !binding.rbSi.isSelected

    }
    fun setSiguentePregunta(){
        if(!validarRespuesta()) {
            var respuesta: String = if (binding.rbNo.isSelected) "No" else "Si"

            // Guardar la Pregunta anterior en un list
            listaRespuestasPreguntas.add(
                modeloPreguntas(
                    categorias[indicadorCategoria].categoria.id,
                    emociones.result[indicadorEmocion].id,
                    listaPreguntas.get(indicadorPregunta).id,
                    respuesta
                    )
            )

            if (binding.rbNo.isSelected) {
                indicadorEmocion = emocionRandon()
                setListadoPreguntas()
            } else {
                indicadorPregunta++
                if(indicadorPregunta>= listaPreguntas.size){
                    indicadorCategoria++
                    setListadoPreguntas()
                }else {
                    indicadorPregunta++
                }
            }
            //Setear Nueva Pregunta
            setPregunta()

        }else{
            Toast.makeText(requireContext(), "Por favor seleccione una respuesta.", Toast.LENGTH_SHORT).show()

        }

    }
}