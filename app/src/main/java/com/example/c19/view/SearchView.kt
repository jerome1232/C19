package com.example.c19.view

interface SearchView {

    fun drawCard(entityMap : Map<String, Any?>, entityName : String)

    fun showAdded(name: String)

    fun showDeleted(name: String)
}