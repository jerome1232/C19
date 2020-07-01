package com.example.c19.presenter

import com.example.c19.model.CovidStateManager
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomePresenterImpl(covidManager: CovidStateManager, homeView: HomeView) : HomePresenter {
    private val _covidStateManager: CovidStateManager = covidManager
    private val _homeView: HomeView = homeView

    override fun getStateData(state: String) {
        doAsync {
            val stateData = _covidStateManager.getState(state)
            println(stateData)
            uiThread {
                _homeView.setStateData(stateData)
            }
        }
    }

}