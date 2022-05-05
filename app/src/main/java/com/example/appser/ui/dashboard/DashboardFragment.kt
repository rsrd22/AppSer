package com.example.appser.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.Fragment
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.appser.R


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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