package com.example.c19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * For Data Model Testing Purposes only
     **/
    fun dataTest(view: View) {
        val manager: CovidManager = CovidManager()
        manager.fetchApi()
    }
}