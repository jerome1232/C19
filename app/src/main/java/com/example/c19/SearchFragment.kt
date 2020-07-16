package com.example.c19

import UICard.CardPageAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.view.HomeView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * This Fragment is responsible for getting a country or state input from the user and
 * displaying the card with the relevant data for the entity the user put in. Thanks to Jeremy,
 * GPS location will get the location and auto-populate the input field for the user.
 *
 * @author Chase Moses
 */

class SearchFragment : Fragment(), HomeView, View.OnClickListener {

    private val _homePresenter = HomePresenterImpl(CovidManager(), this)
    private var _viewPager: ViewPager? = null
    private var _cardAdapter: CardPageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_search, container, false)

        _viewPager = view.findViewById(R.id.cardSearchViewPager)
        _cardAdapter = CardPageAdapter()

        val btnGo = view.findViewById<Button>(R.id.btn_go)
        btnGo.setOnClickListener(this)


        return view
    }


    override fun drawFavorites(favorites: List<Map<String, Any?>>) {
        TODO("Not yet implemented")
    }

    // On Click for the Go button
    override fun onClick(v: View?) {

        val inputResult = view?.findViewById<EditText>(R.id.searchInputBar)
        val result = inputResult?.text.toString()

        Log.i("Input Test", result)


        doAsync {
            val entity = _homePresenter.getEntity(result)

            uiThread {
                Log.i("Search Result Test", entity.toString())
            }


        }
    }
}