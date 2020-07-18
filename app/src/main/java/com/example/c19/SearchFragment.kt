package com.example.c19

import UICard.CardFragmentPageAdapter
import UICard.CardPageAdapter
import UICard.ShadowTransformer
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.presenter.SearchPresenter
import com.example.c19.presenter.SearchPresenterImpl
import com.example.c19.view.HomeView
import com.example.c19.view.SearchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.Map as Map1

/**
 * This Fragment is responsible for getting a country or state input from the user and
 * displaying the card with the relevant data for the entity the user put in. Thanks to Jeremy,
 * GPS location will get the location and auto-populate the input field for the user.
 *
 * @author Chase Moses
 */

class SearchFragment : Fragment(), View.OnClickListener, SearchView {

    private val _searchPresenter = SearchPresenterImpl(CovidManager(), this)
    private var _viewPager: ViewPager? = null
    private var _cardAdapter: CardPageAdapter? = null
    private var _CardShadowTransformer: ShadowTransformer? = null
    private var _FragmentCardAdapter: CardFragmentPageAdapter? = null
    private var _FragmentCardShadowTransformer: ShadowTransformer? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_search, container, false)

        _viewPager = view.findViewById(R.id.cardSearchViewPager)
        _cardAdapter = CardPageAdapter()
        _FragmentCardAdapter = CardFragmentPageAdapter(
            fragmentManager,
            dpToPixels(2, this)
        )
        _CardShadowTransformer = ShadowTransformer(_viewPager, _cardAdapter!!)
        _FragmentCardShadowTransformer = ShadowTransformer(_viewPager, _FragmentCardAdapter!!)
        _viewPager?.adapter = _cardAdapter
        _viewPager?.setPageTransformer(false, _CardShadowTransformer)
        _viewPager?.offscreenPageLimit = 3

        val btnGo = view.findViewById<Button>(R.id.btn_go)
        btnGo.setOnClickListener(this)


        return view
    }

    // On Click for the Go button
    override fun onClick(v: View?) {

        val inputResult = view?.findViewById<EditText>(R.id.searchInputBar)
        val result = inputResult?.text.toString()

        _searchPresenter.getEntity(result)

        Log.i("Input Test", result)




    }
    companion object {
        fun dpToPixels(dp: Int, context: SearchFragment): Float {
            return dp * context.resources.displayMetrics.density
        }


    }

    override fun drawCard(entityMap : kotlin.collections.Map<String, Any?>) {
        _cardAdapter!!.addCardItem(entityMap)
        _cardAdapter!!.notifyDataSetChanged()
    }

}