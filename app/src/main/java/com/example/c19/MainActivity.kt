package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.c19.model.CovidGlobalManager
import com.example.c19.model.CovidStateManager
import com.example.c19.model.GlobalCovid
import com.example.c19.model.StateUsCovid
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.math.log

class MainActivity : AppCompatActivity(), HomeView {
    private val covidStateManager = CovidStateManager()
    private val homePresenter = HomePresenterImpl(covidStateManager, this)
    private val TAG = "mainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * For Data Model Testing Purposes only
     **/
    fun dataTest(view: View) {
//        homePresenter.getStateData(state = "california")
        val manager = CovidGlobalManager()
        doAsync {
            val test = manager.getGlobal()
            Log.i(TAG, test.toString())
            uiThread {
                if (test != null) {
                    toast(test.global.newConfirmed.toString())
                }
            }
        }
    }

    override fun setStateData(stateUsCovid: StateUsCovid?) {
        findViewById<TextView>(R.id.text).text = stateUsCovid.toString()
        val TAG = "MainActivity.dataTest()"
        Log.i(TAG, stateUsCovid.toString())
    }
}