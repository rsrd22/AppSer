package com.example.appser.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.model.relations.CategoriasWithPreguntas
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.preference.SerApplication.Companion.prefs
import com.example.appser.data.resource.CategoriasDataSource
import com.example.appser.data.resource.CicloVitalDataResource
import com.example.appser.data.resource.EmocionesDataSource
import com.example.appser.databinding.FragmentDashboardBinding
import com.example.appser.presentation.*
import com.example.appser.repository.CategoriasRepositoryImpl
import com.example.appser.repository.CicloVitalRepositoryImpl
import com.example.appser.repository.EmocionesRepositoryImpl
import kotlinx.android.synthetic.main.activity_menu_test.*
import kotlinx.android.synthetic.main.content_menu_test.*
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var personaAndUsuario: PersonaAndUsuario
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var appDatabase: AppDatabase
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var categorias: List<CategoriasWithPreguntas>
    private lateinit var emociones: EmocionesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getDatabase(requireContext())

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        mainViewModel.getPersonaAndUsuario().observe(viewLifecycleOwner, Observer { result ->
            personaAndUsuario = result
            if (personaAndUsuario != null) {
                setBienvenidoUsuario()
                getCicloVital()
            }
        })
        Log.d("Dashboard", "OnViewCreated")
        btn_identify_emotion.setOnClickListener {
            identificarEmocion()
        }

        getAllCategorias()
        getAllEmociones()

    }

    private fun loadData(){

    }

    private fun getCicloVital() {
        val viewModelCiclo by viewModels<CicloVitalViewModel> {
            CicloVitalViewModelFactory(
                CicloVitalRepositoryImpl(
                    CicloVitalDataResource(
                        appDatabase.ciclovitalDao()
                    )
                )
            )
        }
        var edad: Int = personaAndUsuario.persona.edad?:0
        viewModelCiclo.fetchCicloVitalByEdad(edad).observe(viewLifecycleOwner, Observer {result ->
            when (result) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {

                    mainViewModel.setCicloVital(result.data)
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

    private fun getAllEmociones() {
        val viewModel by viewModels<EmocionesViewModel> {
            EmocionesViewModelFactory(
                EmocionesRepositoryImpl(
                    EmocionesDataSource(
                        appDatabase.emocionesDao()
                    )
                )
            )
        }

        viewModel.fetchAllEmociones().observe(viewLifecycleOwner, Observer {result ->
            when (result) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    emociones = result.data

                    mainViewModel.setEmociones(emociones)
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

    private fun getAllCategorias() {
        val viewModel by viewModels<CategoriasViewModel> {
            CategoriasViewModelFactory(
                CategoriasRepositoryImpl(
                    CategoriasDataSource(
                        appDatabase.categoriasDao()
                    )
                )
            )
        }
        Log.d("Dashboard", "getAllCategorias")
        viewModel.fetchAllCategoriasWithPreguntas().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Resource.Loading -> {
//                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    categorias = result.data
                    Log.d("Dashboard", "categorias: ${categorias}")
                    mainViewModel.setCategoriasWithPreguntas(categorias)
//                    Toast.makeText(requireContext(), "End Carga ..${emociones}", Toast.LENGTH_SHORT).show()
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

    fun setBienvenidoUsuario(){
        binding.txtUser.text = "Bienvenido: ${personaAndUsuario.persona.nombre_completo}"
    }

    fun identificarEmocion(){
        findNavController().navigate(R.id.action_dashboardFragment_to_questionsFragment)
    }

    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }*/

}