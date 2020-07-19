package com.example.c19.presenter

import com.example.c19.model.*
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

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
                        "Date:" to covidEntity.date.substring(0, 10),
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
                        "Date:" to unixTimeStampToString(covidEntity.date.toLong()),
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
                        "Date:" to unixTimeStampToString(covidEntity.date.toLong()),
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

    /**
     * Adds a favorite to favorites list
     *
     * @author Jeremy D. Jones
     * @param name
     */
    override fun addFavorite(name: String) {
    }

    /**
     * Checks whether something is a favorite or not
     *
     * @author Jeremy D. Jones
     * @param name
     * @return
     */
    override fun isFavorite(name: String) : Boolean {
        return _covidStateManager.isFavorite(name)
    }

    /**
     * Removes a favorite from the favorite list
     *
     * @author Jeremy D. Jones
     * @param name
     */
    override fun delFavorite(name: String) {
        doAsync {
            _covidStateManager.delFavorite(name)
            uiThread {
                _homeView.removeFavorite(name)
            }
        }
    }

    private fun unixTimeStampToString(unixTimeStamp: Long): String {
        val df = Date(unixTimeStamp)
        return SimpleDateFormat("yyyy-dd-MM").format(df)
    }

}