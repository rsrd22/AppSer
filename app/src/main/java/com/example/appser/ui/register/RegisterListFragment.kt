package com.example.appser.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.example.appser.R
import com.example.appser.core.Resource
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.HistoricoCuestionario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.resource.PersonaDataSource
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.FragmentRegisterListBinding
import com.example.appser.presentation.*
import com.example.appser.repository.PersonaRepositoryImpl
import com.example.appser.repository.UsuarioRepositoryImpl
import com.example.appser.ui.register.adapters.RegisterListAdapter

class RegisterListFragment : Fragment(R.layout.fragment_register_list), RegisterListAdapter.OnRegisterListClickListener {

    private lateinit var adapter: RegisterListAdapter
    private lateinit var binding: FragmentRegisterListBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var personaAndUsuario: PersonaAndUsuario


    private val viewModel by viewModels<PersonaViewModel>{
        PersonaViewModelFactory(
            PersonaRepositoryImpl(
                PersonaDataSource(
                    AppDatabase.getDatabase(requireContext()).personaDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterListBinding.bind(view)

        mainViewModel.getPersonaAndUsuario().observe(viewLifecycleOwner, Observer { result ->
            personaAndUsuario = result
            if (personaAndUsuario != null) {
                cargarListaCuestionario()
            }
        })


    }

    fun cargarListaCuestionario(){
        viewModel.fetchHistoricoCuestionario(personaAndUsuario.persona.id).observe(viewLifecycleOwner, Observer{result->
            when(result){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT)
                }
                is Resource.Success -> {
                    adapter = RegisterListAdapter(result.data, this@RegisterListFragment)
                    binding.rvListRegister.adapter = adapter
                }
                is Resource.Failure -> {
                    Log.d("ErrorPersonCuestionario", "${result.exception}")
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

    override fun onRegisterListClick(historicoCuestionario: HistoricoCuestionario) {
        Log.d("ItemHistorico", "Activit - ${historicoCuestionario}")
        Toast.makeText(requireContext(), "Usuario: ${historicoCuestionario.emocion}", Toast.LENGTH_LONG).show()
    }
}