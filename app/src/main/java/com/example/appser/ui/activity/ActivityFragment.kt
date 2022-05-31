package com.example.appser.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.appser.R
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.databinding.FragmentActivityBinding
import com.example.appser.databinding.FragmentEmotionBinding
import com.example.appser.presentation.MainViewModel
import kotlinx.android.synthetic.main.fragment_activity.*

class ActivityFragment : Fragment(R.layout.fragment_activity) {

    private lateinit var binding: FragmentActivityBinding
    private lateinit var appDatabase: AppDatabase
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var actividadAsignada : ActividadesEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getDatabase(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentActivityBinding.bind(view)


        mainViewModel.getActividadAsignada().observe(viewLifecycleOwner, Observer{ result->
            actividadAsignada = result
            MostrarActividad()
        })

        btnFinalizar.setOnClickListener(){
            Toast.makeText(requireContext(), "Se ha asignado la actividad", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_activityFragment2_to_dashboardFragment)
        }

    }

    fun MostrarActividad(){
        binding.txtNombreActividad.text = actividadAsignada.titulo
        binding.txtDescripcionActividad.text = actividadAsignada.descripcion
        if(actividadAsignada.enlace == ""){

        }else{

        }

    }

}