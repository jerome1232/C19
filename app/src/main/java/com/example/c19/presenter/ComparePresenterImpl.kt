package com.example.c19.presenter

import com.example.c19.model.CovidHistManager
import com.example.c19.view.CompareView
import com.example.c19.view.HistoricalDataView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/** Historical Data presenter implementation
 * @author Rodrigo Iturralde
 */
class ComparePresenterImpl(
    covidHistManager: CovidHistManager,
    compareView: CompareView) : ComparePresenter {

    private val _covidHistManager = covidHistManager
    private val _compareView = compareView

    override fun getHistoricalData(entityName1: String, entityName2: String) {
        doAsync {
            val entity1 = _covidHistManager.getHistEntity(entityName1)
            val entity2 = _covidHistManager.getHistEntity(entityName2)
            uiThread {
                _compareView.drawComparisonChart(entity1, entity2)
            }
        }
    }

}