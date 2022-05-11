package com.example.appser.ui.dashboard

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.appser.R
import com.example.appser.presentation.MainViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_menu_test.*
import kotlinx.android.synthetic.main.app_bar_menu_test.*


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)


 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // var drawerLayout : DrawerLayout77777777777777777777777777777777777777-swt5r_open)
        //drawer_layout.addDrawerListener(toggle)
        //toggle.syncState()


    }


    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
        
    }

//override fun onOptionsItemSelected(item: MenuItem): Boolean {
  //  val id: Int = item.getItemId()
    //if (id == R.id.identify_emotion) {

    //}
      //  return true
        // lo ideal aquí sería hacer un intent para abrir una nueva clase como lo siguiente
        //Log.i("ActionBar", "Settings!")
        //val about = Intent(
          //  ApplicationProvider.getApplicationContext<Context>(),
            //About::class.java
        //)
        //startActivity(about)
       // return true
    //}
    //return super.onOptionsItemSelected(item)
//}
}