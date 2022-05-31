package com.example.appser.ui.register

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.resource.PersonaDataSource
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.FragmentRegisterBinding
import com.example.appser.presentation.PersonaViewModel
import com.example.appser.presentation.PersonaViewModelFactory
import com.example.appser.presentation.UsuarioViewModel
import com.example.appser.presentation.UsuarioViewModelFactory
import com.example.appser.repository.PersonaRepositoryImpl
import com.example.appser.repository.UsuarioRepositoryImpl
import java.time.Instant


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(
            UsuarioRepositoryImpl(
                UsuarioDataSource(AppDatabase.getDatabase(requireContext()).usuarioDao())
            )
        )
    }
    private val viewModelPersona by viewModels<PersonaViewModel> {
        PersonaViewModelFactory(
            PersonaRepositoryImpl(
                PersonaDataSource(AppDatabase.getDatabase(requireContext()).personaDao())
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        val btnRegistarse = binding.btnRegister  //  view.findViewById<Button>(R.id.btnRegister)

        btnRegistarse.setOnClickListener{
            val txtNombre = binding.txtNombreCompleto
            val txtEdad = binding.txtEdad
            val txtGenero = binding.txtGenero
            val txtEmail = binding.txtEmail
            var persona = PersonaEntity(nombre_completo = txtNombre.text.toString(), edad = Integer.parseInt(txtEdad.text.toString()), genero = txtGenero.text.toString(), user_create =  "SAdmin", create_at =  "${Instant.now().toString()}")

            persona.usuario = UsuarioEntity(0, txtEmail.text.toString(), 1, 0, 1, "SAdmin", "${Instant.now().toString()}")


            viewModel.fetchPersonaWithUsuario(persona).observe(viewLifecycleOwner, Observer{result ->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save exitoso..", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.homeFragment)
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

}