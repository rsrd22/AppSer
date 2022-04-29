package com.example.appser.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.FragmentRegisterListBinding
import com.example.appser.presentation.UsuarioViewModel
import com.example.appser.presentation.UsuarioViewModelFactory
import com.example.appser.repository.UsuarioRepositoryImpl

class RegisterListFragment : Fragment(R.layout.fragment_register_list) {

    private lateinit var binding: FragmentRegisterListBinding

    private val viewModel by viewModels<UsuarioViewModel>{
        UsuarioViewModelFactory(
            UsuarioRepositoryImpl(
                UsuarioDataSource(
                    AppDatabase.getDatabase(requireContext()).usuarioDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterListBinding.bind(view)

        viewModel.fetchPersonaAndUsuario().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading->{
                    Toast.makeText(requireContext(), "Cargando..", Toast.LENGTH_LONG).show()
                }
                is Resource.Success->{

                }
                is Resource.Failure -> {
                    Log.d("ErrorPersonaAndUsuario", "${result.exception}")
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