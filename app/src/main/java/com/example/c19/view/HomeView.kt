package com.example.c19.view

/**
 * Home view interface
 */
interface HomeView {
    /**
     * Calls the function in the view that updates the card carousel of favorites
     */
    fun drawFavorites(favorites: List<Map<String, Any?>>)
}