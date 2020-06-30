package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.c19.model.CovidStateManager
import com.example.c19.model.StateUsCovid
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.view.HomeView

class MainActivity : AppCompatActivity(), HomeView {
    private val covidStateManager = CovidStateManager()
    private val homePresenter = HomePresenterImpl(covidStateManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * For Data Model Testing Purposes only
     **/
    fun dataTest(view: View) {
        homePresenter.getStateData(state = "california")
    }

    override fun setStateData(stateUsCovid: StateUsCovid?) {
        findViewById<TextView>(R.id.text).text = stateUsCovid.toString()
        val TAG = "MainActivity.dataTest()"
        Log.i(TAG, stateUsCovid.toString())
    }
}