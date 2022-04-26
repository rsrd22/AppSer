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
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import kotlinx.android.synthetic.main.fragment_home.*


class homeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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