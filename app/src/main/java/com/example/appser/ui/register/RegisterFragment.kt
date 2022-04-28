package com.example.appser.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.UsuarioEntity
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.FragmentRegisterBinding
import com.example.appser.presentation.UsuarioViewModel
import com.example.appser.presentation.UsuarioViewModelFactory
import com.example.appser.repository.UsuarioRepositoryImpl


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<UsuarioViewModel> {
        UsuarioViewModelFactory(
            UsuarioRepositoryImpl(
                UsuarioDataSource(AppDatabase.getDatabase(requireContext()).usuarioDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        val btnRegistarse = binding.btnRegister  //  view.findViewById<Button>(R.id.btnRegister)

        btnRegistarse.setOnClickListener{
            val txtNombre = binding.txtNombreCompleto
            val txtEdad = binding.txtEdad
            val txtGenero = binding.txtGenero
            val txtEmail = binding.txtEmail

            val usuario =  UsuarioEntity(0, txtEmail.text.toString(), 1, 1, "rsrd", "2022-04-27")
            viewModel.fetchSaveUsuario(usuario).observe(viewLifecycleOwner, Observer{result ->
                when(result){
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "Save exitoso..", Toast.LENGTH_LONG).show()
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