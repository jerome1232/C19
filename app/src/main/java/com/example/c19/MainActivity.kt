package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
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

        // Remove the default name in the Toolbar
/*        setSupportActionBar(toolbar)
          supportActionBar?.setDisplayShowTitleEnabled(false)
          val toolbarTitle = findViewById<TextView>(R.id.toolbar) // Use our own TextView in the toolbar
          val mTitle = toolbarTitle.findViewById<TextView>(R.id.toolbar_title)

 */
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

    /**
     * TODO REMOVE ME AT WILL
     *
     * I'm just here for testing
     *
     * @param view
     */
    fun dataCountryTest(view: View) {
        val manager = CovidManager()
        doAsync {
            val country = manager.getEntity("Bolivia")
            uiThread {
                Log.i("dataTest", country.toString())
            }
        }
        doAsync {
            val state = manager.getEntity("Idaho")
            uiThread {
                Log.i("dataTest", state.toString())
            }
        }
        doAsync {
            val globalStats = manager.getGlobal()
            uiThread {
                Log.i("dataTest", globalStats.toString())
            }
        }
    }


}