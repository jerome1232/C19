package com.example.c19

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.c19.model.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var compareFragment: CompareFragment
    lateinit var historicalDataFragment: HistoricalDataFragment
    lateinit var settingsFragment: SettingsFragment
    // Needed for GPS data
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create variables for the different layouts and tie the layout id to them
        setSupportActionBar(toolbar)

        navigationView.setNavigationItemSelectedListener(this)


        // Setting title based on Fragment name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


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

        // Needed for GPS data
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
     * Start the search_fragment for finding different cards to add to the home_fragment viewPager
     *
     * @author Chase Moses
     */

    fun btnStartSearch(view : View) {

        // Create a new SearchFragment, and use FragmentManager to open the fragment_search view.
        val searchFragment = SearchFragment()
        var transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, searchFragment)
        transaction.addToBackStack(null)
        transaction.commit()


        Toast.makeText(this, "Button Test Successful",  Toast.LENGTH_SHORT).show()
    }

    /**
     * Take user input to obtain correct entity based on input.
     *
     * @author Chase Moses
     */

    fun btnGetCard() {

}

    /**
     * Get's the devices last known location and finds
     * the users country/state using that information
     *
     * @author Jeremy D. Jones
     * @param view
     */
    fun gpsRequest(view: View) {
        val TAG = "gpsRequest"
        val RECORD_REQUEST_CODE = 101
        var name: String

        // Checking to see if we have permission to use location services.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Requesting permission if we don't have it
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                RECORD_REQUEST_CODE
            )
            return
        }
        // requesting the last known location
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.i(TAG, location.toString())


            val addresses: List<Address>
            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())

            // If we got a location, get the country, if it's the USA, get the state
            if (location != null) {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                name = addresses.get(0).countryName
                if (name == "United States") name = addresses.get(0).adminArea
                Toast.makeText(this, "Location: $name", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No location data", Toast.LENGTH_SHORT).show()
            }
        }
        return
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
            val globalStats = manager.getEntity("global")
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