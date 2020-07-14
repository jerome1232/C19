package com.example.c19.presenter

import com.example.c19.model.CountryCovid
import com.example.c19.model.CovidManager
import com.example.c19.model.GlobalCovid
import com.example.c19.model.StateUsCovid
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Converts the retrieved data from the model to an map object understandable by the view, then
 * calls the method which updates the carousel of cards in the view
 *
 * @author Rodrigo Iturralde
 * @param covidManager
 * @param homeView
 */
class HomePresenterImpl(covidManager: CovidManager, homeView: HomeView) : HomePresenter {
    private val _covidStateManager: CovidManager = covidManager
    private val _homeView: HomeView = homeView

    override fun getFavorites() {
        doAsync {
            // Request from model
            val favorites = _covidStateManager.getFavorites()

            // Build and pass a maps list of covid entities
            val favoritesAsMaps = mutableListOf<Map<String, Any?>>()
            favorites.forEach{ covidEntity ->
                if (covidEntity is GlobalCovid) {
                    val covidEntityAsMap = mapOf<String, Any?>(
                        "title" to "Global",
                        "Date:" to covidEntity.date,
                        "New confirmed:" to covidEntity.newConfirmed,
                        "New recovered:" to covidEntity.newRecovered,
                        "New deaths:" to covidEntity.newDeaths,
                        "Total confirmed:" to covidEntity.totalConfirmed,
                        "Total recovered:" to covidEntity.totalRecovered,
                        "Total deaths:" to covidEntity.totalDeaths
                    )
                    favoritesAsMaps.add(covidEntityAsMap)
                } else if (covidEntity is CountryCovid) {
                    val covidEntityAsMap = mapOf<String, Any?>(
                        "title" to covidEntity.name,
                        "Date:" to covidEntity.date,
                        "New confirmed:" to covidEntity.newConfirmed,
                        "New recovered:" to covidEntity.newRecovered,
                        "New deaths:" to covidEntity.newDeaths,
                        "Total confirmed:" to covidEntity.totalConfirmed,
                        "Total recovered:" to covidEntity.totalRecovered,
                        "Total deaths:" to covidEntity.totalDeaths
                    )
                    favoritesAsMaps.add(covidEntityAsMap)
                } else if (covidEntity is StateUsCovid) {
                    val covidEntityAsMap = mapOf<String, Any?>(
                        "title" to covidEntity.name,
                        "Date:" to covidEntity.date,
                        "New confirmed:" to covidEntity.newConfirmed,
                        "New deaths:" to covidEntity.newDeaths,
                        "Total confirmed:" to covidEntity.totalConfirmed,
                        "Total deaths:" to covidEntity.totalDeaths
                    )
                    favoritesAsMaps.add(covidEntityAsMap)
                }
            }
            uiThread {
                _homeView.drawFavorites(favoritesAsMaps)
            }
        }
    }

}