package com.example.appser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.appser.R
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.RolEntity
import com.example.appser.data.resource.RolDataSource
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.presentation.RolViewModel
import com.example.appser.presentation.RolViewModelFactory
import com.example.appser.presentation.UsuarioViewModel
import com.example.appser.presentation.UsuarioViewModelFactory
import com.example.appser.repository.RolRepositoryImpl
import com.example.appser.repository.UsuarioRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}