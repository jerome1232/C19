package com.example.c19

import UICard.CardPageAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.view.HomeView
import com.google.android.gms.location.LocationServices
import org.jetbrains.anko.doAsync


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


//





        return view
    }


    override fun drawFavorites(favorites: List<Map<String, Any?>>) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {

        val inputResult = view?.findViewById<EditText>(R.id.searchInput)

        val result = inputResult?.text.toString()

        Log.i("Input Test", result)

//        val entity = _homePresenter.getEntity(result)
//
//        doAsync {
//


        //}
    }
}