package com.example.appser.ui.emotion

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.resource.ActividadesDataSource
import com.example.appser.data.resource.CuestionarioDataSource
import com.example.appser.data.resource.RolDataSource
import com.example.appser.databinding.FragmentEmotionBinding
import com.example.appser.databinding.FragmentQuestionsBinding
import com.example.appser.databinding.ItemActivitiesBinding
import com.example.appser.presentation.*
import com.example.appser.repository.ActividadesRepositoryImpl
import com.example.appser.repository.CuestionarioRepositoryImpl
import com.example.appser.repository.RolRepositoryImpl
import com.example.appser.ui.activity.ActivityFragmentDirections
import com.example.appser.ui.emotion.adapters.ActivitiesListAdapter
import kotlinx.android.synthetic.main.fragment_emotion.*
import kotlinx.android.synthetic.main.fragment_questions.*
import java.text.SimpleDateFormat
import java.util.*


class EmotionFragment : Fragment(R.layout.fragment_emotion), ActivitiesListAdapter.OnActivitiesListClickListener {

    private lateinit var binding: FragmentEmotionBinding
    private lateinit var appDatabase: AppDatabase
    private val mainViewModel: MainViewModel by activityViewModels()
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private val currentdate = sdf.format(Date())
    private lateinit var personaAndUsuario: PersonaAndUsuario
    private lateinit var ciclovital: CicloVitalEntity
    private lateinit var emocioncuestionario: Pair<Long, Long>
    private lateinit var emociones: EmocionesList
    private lateinit var emocion: EmocionesEntity
    private lateinit var actividades: List<ActividadesEntity>
    private lateinit var adapter: ActivitiesListAdapter
    private lateinit var actividadAsignada : ActividadesEntity
    private var actividadIndex: Int=-1

    val viewModelActividades by viewModels<ActividadesViewModel> {
        ActividadesViewModelFactory(
            ActividadesRepositoryImpl(
                ActividadesDataSource(
                    appDatabase.actividadesDao()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getDatabase(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmotionBinding.bind(view)


        mainViewModel.getPersonaAndUsuario().observe(viewLifecycleOwner, Observer{ result->
            personaAndUsuario = result
        })

        mainViewModel.getCicloVital().observe(viewLifecycleOwner, Observer{ result->
            ciclovital = result
        })

        mainViewModel.getEmocionCuestionarioId().observe(viewLifecycleOwner, Observer { datos->
            emocioncuestionario = datos

            if(emocioncuestionario.first > 0) {
                mainViewModel.getEmociones().observe(viewLifecycleOwner, Observer { result ->
                    Log.d("Emociones-->", "Emo->${result}")
                    emociones = result

                    emocion = emociones.result.filter { it.id == emocioncuestionario.first }.get(0)
                    if(emocion != null ){
                        MostrarEmocion()
                    }
                })
            }else{ // COLOCAR OBSERVACION DE NO SE PUDO ENCONTRAR LA EMOCION

            }
        })

        btnVerActividad.setOnClickListener {
            asignarActividad()
        }

    }

    fun asignarActividad(){
        if(actividadIndex == -1){
            Toast.makeText(requireContext(), "Por favor seleccione una actividad para seguir con el proceso.", Toast.LENGTH_SHORT).show()
        }else{
            val viewModelCuestionario by viewModels<CuestionarioViewModel> {
                CuestionarioViewModelFactory(
                    CuestionarioRepositoryImpl(
                        CuestionarioDataSource(
                            appDatabase.cuestionarioDao()
                        )
                    )
                )
            }

            viewModelCuestionario.fetchUpdateCuestionario(emocioncuestionario.second, actividadAsignada.id).observe(viewLifecycleOwner, Observer{result ->
                when(result){
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success ->{
                        mainViewModel.setActividadAsignada(actividadAsignada)
                        findNavController().navigate(R.id.action_emotionFragment2_to_activityFragment2)
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
    }

    fun MostrarEmocion(){
        binding.txtNombreEmocion.text = emocion.nombre
        binding.txtDescripcionEmocion.text = emocion.descripcion
        cargarListaActividades()
    }

    fun cargarListaActividades(){
        viewModelActividades.fetchActividadByEmocioByCiclo(emocion.id, ciclovital.id).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading ->{
                    Toast.makeText(requireContext(), "Cargado Actividades..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    actividades = result.data
                    Log.d("Actividades", "Lista -- ${actividades}")
                    adapter = ActivitiesListAdapter(actividades, this@EmotionFragment)

                    binding.rvListActivities.adapter = adapter
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

    override fun onActivitiesListClick(itemBinding: ItemActivitiesBinding, actividad: ActividadesEntity) {

        for(index in 0..binding.rvListActivities.childCount-1){
            if(binding.rvListActivities.get(index) == itemBinding.root){
                if(actividadIndex == index){
                    itemBinding.root.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                    actividadIndex = -1
                }else {
                    actividadIndex = index
                    itemBinding.root.setBackgroundColor(Color.parseColor("#4B00BCD4"))
                    actividadAsignada = actividad
                }
            }else{
                binding.rvListActivities.get(index).setBackgroundColor(Color.parseColor("#00FFFFFF"))
            }
        }
    }
}