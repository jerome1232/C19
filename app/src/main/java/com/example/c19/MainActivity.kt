package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * For Data Model Testing Purposes only
     **/
    fun dataTest() {
        val TAG = "MainActivity.dataTest()"
        val manager = CovidManager()
        val state = manager.getState("california")
        Log.i(TAG, state.toString())
    }
}