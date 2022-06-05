package com.example.appser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appser.R
import com.example.appser.data.local.AppDatabase
import com.example.appser.data.model.RolEntity
import com.example.appser.data.preference.SerApplication
import com.example.appser.data.preference.SerApplication.Companion.prefs
import com.example.appser.data.resource.RolDataSource
import com.example.appser.data.resource.UsuarioDataSource
import com.example.appser.databinding.ActivityMenuTestBinding
import com.example.appser.presentation.RolViewModel
import com.example.appser.presentation.RolViewModelFactory
import com.example.appser.presentation.UsuarioViewModel
import com.example.appser.presentation.UsuarioViewModelFactory
import com.example.appser.repository.RolRepositoryImpl
import com.example.appser.repository.UsuarioRepositoryImpl
import com.example.appser.ui.home.homeFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_menu_test.*
import kotlinx.android.synthetic.main.app_bar_menu_test.*
import kotlinx.android.synthetic.main.content_menu_test.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMenuTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenuTest.toolbar)

        /*binding.appBarMenuTest.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu_test)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController.addOnDestinationChangedListener { navController, destination, arguments ->
            if (destination.id == R.id.homeFragment) {
                binding.appBarMenuTest.toolbar.navigationIcon = null

            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.dashboardFragment, R.id.questionsFragment2, R.id.registerListFragment,
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }}
    }
    override fun onStart() {
        super.onStart()
        LoadData()
    }

    fun LoadData(){
        val vistaHeader: View = binding.navView.getHeaderView(0)
        val tvNombreCompleto: TextView = vistaHeader.findViewById(R.id.txtNombreCompleto)
        val tvCorreo: TextView = vistaHeader.findViewById(R.id.txtCorreo)
        tvNombreCompleto.text = SerApplication.prefs.getName()
        tvCorreo.text = SerApplication.prefs.getEmail()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.main_menu, menu)
            return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu_test)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout) {
            Logout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun Logout(){
        prefs.wipe()
        irHome()
    }

    fun irHome(){
        val home: Intent = Intent(this, MainActivity::class.java)
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(home)
    }
}
