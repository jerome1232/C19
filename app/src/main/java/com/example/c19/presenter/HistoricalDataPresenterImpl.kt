package com.example.c19.presenter

import com.example.c19.model.CovidHistManager
import com.example.c19.view.HistoricalDataView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/** Historical Data presenter implementation
 * @author Rodrigo Iturralde
 */
class HistoricalDataPresenterImpl(
    covidHistManager: CovidHistManager,
    historicalDataView: HistoricalDataView) : HistoricalDataPresenter {

    private val _covidHistManager = covidHistManager
    private val _historicalDataView = historicalDataView

    override fun getHistoricalData(entityName: String) {
        doAsync {
            val entity = _covidHistManager.getHistEntity(entityName)
            uiThread {
                _historicalDataView.drawChart(entity)
            }
        }
    }

}