package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.c19.model.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var compareFragment: CompareFragment
    lateinit var historicalDataFragment: HistoricalDataFragment
    lateinit var settingsFragment: SettingsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create variables for the different layouts and tie the layout id to them
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)

        // This enables the 3 hamburger image at the top left of the toolbar
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {}

        // Look for if the button is activated. If so, open the navigation pane
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()

        // Assign home_Fragment as the default fragment to load into on app start
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()




    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when(menuItem.itemId) {
            R.id.home -> {
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

                // Setting title based on Fragment name
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }

            R.id.compare -> {
                compareFragment = CompareFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, compareFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.historical -> {
                historicalDataFragment = HistoricalDataFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, historicalDataFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.settings -> {
                settingsFragment = SettingsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, settingsFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    fun btnStartSearch(view : View) {

        Toast.makeText(this, "Button Test Successful",  Toast.LENGTH_SHORT).show()
    }

    /**
     * TODO REMOVE ME AT WILL
     *
     * I'm just here for testing
     *
     * @param view
     */
    fun dataCountryTest(view: View) {
        val manager = CovidManager()
        val hManager = CovidHistManager()
        doAsync {
            val country = manager.getEntity("Bolivia")
            uiThread {
                if (country is CountryCovid) {
                    println(country.name)
                }
                Log.i("dataTest", country.toString())
            }
        }
        doAsync {
            val state = manager.getEntity("Idaho")
            uiThread {
                if (state is StateUsCovid) {
                    println(state.name)
                }
                Log.i("dataTest", state.toString())
            }
        }
        doAsync {
            val globalStats = manager.getGlobal()
            uiThread {
                if (globalStats is GlobalCovid) {
                    Log.i("dataTest", globalStats.newRecovered.toString())
                }
                Log.i("dataTest", globalStats.toString())
            }
        }
        doAsync {
            val test = hManager.getHistEntity("Idaho")
            uiThread {
                if (test.isNotEmpty()) {
                    if (test[0] is CovidHistState) {
                        Log.i("dataTest", test[0].name)
                    }
                }
                Log.i("dataTest", test.toString())
                var i = 0
                for (item in test) {
                    if (item is CovidHistState) {
                        Log.i("DataTest", i++.toString())
                        Log.i("DataTest", item.name)
                        Log.i("DataTest", item.confirmed.toString())
                        Log.i("DataTest", item.date)
                    }
                }
            }
        }
        doAsync {
            val test = hManager.getHistEntity("Idaho")
            uiThread {
                if (test.isNotEmpty()) {
                    if (test[0] is CovidHistCountry) {
                        Log.i("dataTest", test[0].name)
                    }
                }
                Log.i("dataTest", test.toString())
                var i = 0
                for (item in test) {
                    if (item is CovidHistCountry) {
                        Log.i("DataTest", i++.toString())
                        Log.i("DataTest", item.name)
                        Log.i("DataTest", item.confirmed.toString())
                        Log.i("DataTest", item.date)
                    }
                }
            }
        }
    }


}