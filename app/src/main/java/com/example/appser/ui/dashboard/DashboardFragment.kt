package com.example.appser.ui.dashboard

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.EmocionesList
import com.example.appser.data.model.relations.CategoriasWithPreguntas
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.resource.CategoriasDataSource
import com.example.appser.data.resource.EmocionesDataSource
import com.example.appser.data.resource.PreguntasDataSource
import com.example.appser.databinding.FragmentDashboardBinding
import com.example.appser.databinding.FragmentHomeBinding
import com.example.appser.presentation.*
import com.example.appser.repository.CategoriasRepositoryImpl
import com.example.appser.repository.EmocionesRepositoryImpl
import com.example.appser.repository.PreguntasRepositoryImpl
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_menu_test.*
import kotlinx.android.synthetic.main.app_bar_menu_test.*


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

        mainViewModel.getPersonaAndUsuario().observe(viewLifecycleOwner, Observer { result ->
            personaAndUsuario = result
            if (personaAndUsuario != null) {
                setBienvenidoUsuario()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        val btnIdentify = binding.txtIdentifyEmotion

        btnIdentify.setOnClickListener {
            identificarEmocion()
        }

        getAllCategorias()
        getAllEmociones()

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

        viewModel.fetchAllCategoriasWithPreguntas().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    categorias = result.data
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
        binding.txtUser.text = "Bienvenido NUEVA: ${personaAndUsuario.persona.nombre_completo}"
    }

    fun identificarEmocion(){
        findNavController().navigate(R.id.action_dashboardFragment_to_questionsFragment2)
    }




    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

//override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //  val id: Int = item.getItemId()
    //if (id == R.id.identify_emotion) {

    //}
    //  return true
    // lo ideal aquí sería hacer un intent para abrir una nueva clase como lo siguiente
    //Log.i("ActionBar", "Settings!")
    //val about = Intent(
    //  ApplicationProvider.getApplicationContext<Context>(),
    //About::class.java
    //)
    //startActivity(about)
    // return true
    //}
    //return super.onOptionsItemSelected(item)
//}
}