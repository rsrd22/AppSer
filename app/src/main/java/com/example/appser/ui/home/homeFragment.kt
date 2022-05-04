package com.example.appser.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.CategoriasEntity
import com.example.appser.data.model.CicloVitalEntity
import com.example.appser.data.model.EmocionesEntity
import com.example.appser.data.model.RolEntity
import com.example.appser.data.resource.CategoriasDataSource
import com.example.appser.data.resource.CicloVitalDataResource
import com.example.appser.data.resource.EmocionesDataSource
import com.example.appser.data.resource.RolDataSource
import com.example.appser.presentation.*
import com.example.appser.repository.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class homeFragment : Fragment(R.layout.fragment_home) {

    private val appDatabase: AppDatabase = AppDatabase.getDatabase(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarRoles()
        cargarCiclos()
        cargarCategorias()
        cargarEmociones()



        val btnLogin = view.findViewById<Button>(R.id.btn_Login)
        val btnRegistrarse = view.findViewById<TextView>(R.id.txt_Registrarse)
        val btnLista = view.findViewById<TextView>(R.id.txt_Lista)

        btnLogin.setOnClickListener{
            val txtUsuario = view.findViewById<EditText>(R.id.txt_usuario)
            Toast.makeText(requireContext(), "Usuario: ${txtUsuario.text}", Toast.LENGTH_LONG).show()
        }
        btnRegistrarse.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment2)
        }
        btnLista.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_registerListFragment2)
        }
    }

    fun cargarEmociones(){
        val viewModel by viewModels<EmocionesViewModel> {
            EmocionesViewModelFactory(
                EmocionesRepositoryImpl(
                    EmocionesDataSource(
                        appDatabase.emocionesDao()
                    )
                )
            )
        }

        val emociones = listOf(
            EmocionesEntity(0, "", 1, "SAdmin", "2022-05-03")
        )

        emociones.forEach {
            viewModel.fetchSaveEmocion(it).observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save Emociones exitoso..", Toast.LENGTH_LONG).show()
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

    fun cargarCategorias(){
        val viewModel by viewModels<CategoriasViewModel> {
            CategoriasViewModelFactory(
                CategoriasRepositoryImpl(
                    CategoriasDataSource(
                        appDatabase.categoriasDao()
                    )
                )
            )
        }

        val categorias = listOf(
            CategoriasEntity(0, "", 1, "SAdmin", "2022-05-03")
        )

        categorias.forEach {
            viewModel.fetchSaveCategoria(it).observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save CicloVital exitoso..", Toast.LENGTH_LONG).show()
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

    fun cargarCiclos(){
        val viewModel by viewModels<CicloVitalViewModel> {
            CicloVitalViewModelFactory(
                CicloVitalRepositoryImpl(
                    CicloVitalDataResource(
                        appDatabase.ciclovitalDao()
                    )
                )
            )
        }

        val ciclosVital = listOf(
            CicloVitalEntity(0, "", 1, 5, 1, "SAdmin", "2022-05-03")
        )

        ciclosVital.forEach {
            viewModel.fetchSaveCicloVital(it).observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save CicloVital exitoso..", Toast.LENGTH_LONG).show()
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

    fun cargarRoles(){
        val viewModel by viewModels<RolViewModel>{
            RolViewModelFactory(
                RolRepositoryImpl(
                    RolDataSource(
                        appDatabase.rolDao()
                    )
                )
            )
        }

        val roles =listOf(
            RolEntity(0, "Admin", 1, "SAdmin", "2022-04-28"),
            RolEntity(0, "Usuario", 1, "SAdmin", "2022-04-28")
        )


        roles.forEach{
            viewModel.fetchSaveRol(it).observe(viewLifecycleOwner, Observer {result->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save Rol exitoso..", Toast.LENGTH_LONG).show()
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
        Toast.makeText(requireContext(), "End Cargando Roles..", Toast.LENGTH_LONG).show()
    }
}