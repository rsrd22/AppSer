package com.example.appser.ui.home

import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.RolEntity
import com.example.appser.data.resource.RolDataSource
import com.example.appser.presentation.RolViewModel
import com.example.appser.presentation.RolViewModelFactory
import com.example.appser.repository.RolRepositoryImpl
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class homeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<RolViewModel>{
            RolViewModelFactory(
                RolRepositoryImpl(
                    RolDataSource(
                        AppDatabase.getDatabase(requireContext()).rolDao()
                    )
                )
            )
        }

        val roles = listOf(
            RolEntity(0, "Admin", 1, "SAdmin", "2022-04-28"),
            RolEntity(0, "Usuario", 1, "SAdmin", "2022-04-28")
        )

        Toast.makeText(requireContext(), "Start Cargando Roles..${roles}", Toast.LENGTH_LONG).show()
        roles.forEach{
            Toast.makeText(requireContext(), "Cargando Roles..${it}", Toast.LENGTH_LONG).show()
            viewModel.fetchSaveRol(it)
        }
        Toast.makeText(requireContext(), "End Cargando Roles..", Toast.LENGTH_LONG).show()


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
}