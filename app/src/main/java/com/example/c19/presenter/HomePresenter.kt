package com.example.c19.presenter

/**
 * Home Presenter interface
 *
 * @author Rodrigo Iturralde
 */
interface HomePresenter: FavoritesPresenter {
    /**
     * Request for favorites
     */
    override fun getFavorites()

    override fun addFavorite(name: String)

    override fun isFavorite(name: String) : Boolean

    override fun delFavorite(name: String)
}