package com.example.appser.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.*
import com.example.appser.data.model.relations.CategoriasWithPreguntas
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.resource.CuestionarioPreguntasDataSource
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.FragmentDashboardBinding
import com.example.appser.databinding.FragmentHomeBinding
import com.example.appser.databinding.FragmentQuestionsBinding
import com.example.appser.presentation.*
import com.example.appser.repository.CuestionarioPreguntasRepository
import com.example.appser.repository.CuestionarioPreguntasRepositoryImpl
import com.example.appser.repository.UsuarioRepositoryImpl
import kotlinx.android.synthetic.main.fragment_questions.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max


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
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private val currentdate = sdf.format(Date())

    private lateinit var personaAndUsuario: PersonaAndUsuario

    private val viewModelCuestionarioPreguntas by viewModels<CuestionarioPreguntasViewModel> {
        CuestionarioPreguntaViewModelFactory(
            CuestionarioPreguntasRepositoryImpl(
                CuestionarioPreguntasDataSource(AppDatabase.getDatabase(requireContext()).cuestionariopreguntasDao())
            )
        )
    }


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

        mainViewModel.getPersonaAndUsuario().observe(viewLifecycleOwner, Observer { result ->
            personaAndUsuario = result

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

            // Guardar la Pr egunta anterior en un list
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

            //Validar Categorias
            if(indicadorCategoria < categorias.size){
                //Setear Nueva Pregunta
                setPregunta()
            }else{
                //FINALIZA LA IDENTIFIACION
                //Empieza la Busqueda de la Emocion
                val emocionEncontradaId: Long = getEmocionxRespuestas()

                //Llamado Metodo Guardar
                GuardarCuestionario(emocionEncontradaId)

            }
        }else{
            Toast.makeText(requireContext(), "Por favor seleccione una respuesta.", Toast.LENGTH_SHORT).show()
        }
    }

    fun GuardarCuestionario(emocionEncontradaId: Long){
        var user: String = personaAndUsuario.usuario.email
        var cuestionario = CuestionarioEntity(0, personaAndUsuario.persona.id,-1, currentdate, user, currentdate)
        var lista: MutableList<CuestionarioPreguntasEntity> = mutableListOf()
        var cuestionarioId: Long = 0
        for(item in listaRespuestasPreguntas){
            lista.add(
                CuestionarioPreguntasEntity(0, 0, item.preguntaId, item.respuesta, user, currentdate)
            )
        }

        cuestionario.listaCuestionarioPreguntas = lista
        //Enviar a vista emocionFragment
        viewModelCuestionarioPreguntas.fetchSaveCuestionarioWithCuestionarioPreguntas(cuestionario).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success ->{
                    Toast.makeText(requireContext(), "Save exitoso..", Toast.LENGTH_SHORT).show()
                    cuestionarioId = result.data

                    mainViewModel.setEmocionCuestionarioId(Pair(emocionEncontradaId, cuestionarioId))
                    findNavController().navigate(R.id.action_questionsFragment2_to_emotionFragment2)
                }
                is Resource.Failure -> {
                    Log.d("Error LiveData", "${result.exception}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

    }

    fun getEmocionxRespuestas(): Long{
        var listaRespuestas: List<modeloPreguntas> = listaRespuestasPreguntas.filter { it.respuesta == "Si" }
        var idEmocion : Long = -1
        var sizeEmocion : Int = -1
        var maxEmocion : Int = -1

        for(emocion in emociones.result){
            sizeEmocion = listaRespuestas.filter { it.emocionId == emocion.id }.size
            if(sizeEmocion > maxEmocion ){
                maxEmocion = sizeEmocion
                idEmocion = emocion.id
            }
        }

        return idEmocion
    }
}