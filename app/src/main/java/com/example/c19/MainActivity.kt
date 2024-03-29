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
import android.widget.ToggleButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.c19.model.*
import com.example.c19.presenter.HomePresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var compareFragment: CompareFragment
    lateinit var historicalDataFragment: HistoricalDataFragment
    // Needed for GPS data
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var loc = ""


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
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, searchFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Populates search fragment search bar
     * with gps based location
     *
     * @param view
     */
    fun searchFragGps(view: View) {
        val name = gpsRequest(searchInputBar)
    }

    /**
     * Get's the devices last known location and finds
     * the users country/state using that information
     *
     * @author Jeremy D. Jones
     */
    fun gpsRequest(inputBar: EditText) {
        val TAG = "gpsRequest"
        val RECORD_REQUEST_CODE = 101

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
            Log.i("gps", "Permission Denied")
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
                loc = addresses.get(0).countryName
                if (loc == "United States") loc = addresses.get(0).adminArea
                Log.i("gpsrequest", loc)
                inputBar.setText(loc)
            } else {
                Toast.makeText(this, "No location data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}