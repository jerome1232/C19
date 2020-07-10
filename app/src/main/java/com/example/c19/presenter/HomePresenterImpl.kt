package com.example.c19.presenter

import com.example.c19.model.CovidManager
import com.example.c19.model.StateUsCovid
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomePresenterImpl(covidManager: CovidManager, homeView: HomeView) : HomePresenter {
    private val _covidStateManager: CovidManager = covidManager
    private val _homeView: HomeView = homeView

    override fun getStateData(state: String) {
        doAsync {
            val stateData = _covidStateManager.getEntity(state)
            println(stateData)
            uiThread {
                _homeView.setStateData(stateData as StateUsCovid?)
            }
        }
    }

}