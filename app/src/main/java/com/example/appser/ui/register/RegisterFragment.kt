package com.example.appser.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appser.R
import com.example.appser.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        val btnRegistarse = binding.btnRegister  //  view.findViewById<Button>(R.id.btnRegister)

        btnRegistarse.setOnClickListener{
            val txtNombre = binding.txtNombreCompleto
            val txtEdad = binding.txtEdad
            val txtGenero = binding.txtGenero
            val txtEmail = binding.txtEmail


        }
    }

}