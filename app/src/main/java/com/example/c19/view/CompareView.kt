package com.example.c19.view

import com.example.c19.model.CovidHistEntity

interface CompareView {
    fun drawComparisonChart(covidEntities1: List<CovidHistEntity>, covidEntities2: List<CovidHistEntity>)
}