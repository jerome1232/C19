package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create variables for the different layouts and tie the layout id to them
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer);
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        // Remove the default name in the Toolbar
/*        setSupportActionBar(toolbar)
          supportActionBar?.setDisplayShowTitleEnabled(false)
          val toolbarTitle = findViewById<TextView>(R.id.toolbar) // Create our own TextView in the toolbar
          val mTitle = toolbarTitle.findViewById<TextView>(R.id.toolbar_title)

 */
        // This enables the 3 hamburger image at the top left of the toolbar
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {}

        // Look for if the button is activated. If so, open the navigation pane
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        actionBarDrawerToggle.syncState()

    }
}