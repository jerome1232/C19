package com.example.c19.presenter

import com.example.c19.model.*
import com.example.c19.view.SearchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class SearchPresenterImpl(covidManager: CovidManager, searchView: SearchView) : SearchPresenter {

    private val _covidManager : CovidManager = covidManager
    private val _searchView : SearchView = searchView
    private lateinit var entityMap : Map<String, Any?>

    override fun getEntity(entityName : String) {
        doAsync {

            val entity : CovidEntity? = _covidManager.getEntity(entityName)

            if(entity is GlobalCovid) {
                  entityMap = mapOf<String, Any?>(
                    "title" to "Global",
                    "Date:" to entity.date.substring(0, 10),
                    "New confirmed:" to entity.newConfirmed,
                    "New recovered:" to entity.newRecovered,
                    "New deaths:" to entity.newDeaths,
                    "Total confirmed:" to entity.totalConfirmed,
                    "Total recovered:" to entity.totalRecovered,
                    "Total deaths:" to entity.totalDeaths
                )
            } else if (entity is CountryCovid) {
                entityMap = mapOf<String, Any?>(
                    "title" to entity.name,
                    "Date:" to unixTimeStampToString(entity.date.toLong()),
                    "New confirmed:" to entity.newConfirmed,
                    "New recovered:" to entity.newRecovered,
                    "New deaths:" to entity.newDeaths,
                    "Total confirmed:" to entity.totalConfirmed,
                    "Total recovered:" to entity.totalRecovered,
                    "Total deaths:" to entity.totalDeaths
                )
            } else if (entity is StateUsCovid) {
                entityMap = mapOf<String, Any?>(
                    "title" to entity.name,
                    "Date:" to unixTimeStampToString(entity.date.toLong()),
                    "New confirmed:" to entity.newConfirmed,
                    "New deaths:" to entity.newDeaths,
                    "Total confirmed:" to entity.totalConfirmed,
                    "Total deaths:" to entity.totalDeaths
                )
            }
            uiThread {
                _searchView.drawCard(entityMap)
            }
        }

    }

    private fun unixTimeStampToString(unixTimeStamp: Long): String {
        val df = Date(unixTimeStamp)
        return SimpleDateFormat("yyyy-dd-MM").format(df)
    }
}