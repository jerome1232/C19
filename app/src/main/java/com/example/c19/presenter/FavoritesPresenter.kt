package com.example.c19.presenter

interface FavoritesPresenter {
    fun getFavorites()

    fun addFavorite(name: String)

    fun isFavorite(name: String) : Boolean

    fun delFavorite(name: String)
}