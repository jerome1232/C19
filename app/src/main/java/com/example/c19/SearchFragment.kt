package com.example.c19

import UICard.CardFragmentPageAdapter
import UICard.CardPageAdapter
import UICard.ShadowTransformer
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
import com.example.c19.presenter.SearchPresenterImpl
import com.example.c19.view.HomeView
import com.example.c19.view.SearchView
import kotlinx.android.synthetic.main.card.*

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
        (activity as MainActivity).supportActionBar?.title = getString(R.string.search)

        _viewPager = view.findViewById(R.id.cardSearchViewPager)
        _cardAdapter = CardPageAdapter(_searchPresenter)
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
        val nameResult = inputResult?.text.toString()

        _searchPresenter.getEntity(nameResult)

        Log.i("Input Test", nameResult)






    }

    /**
     * This function is responsible for controlling the favorite button of cards, and adding the
     * cards to the Home Fragment.
     *
     * @author Chase Moses
     */
    private fun toggleFavoriteButton(entityName : String) {
        val toggleButton = view?.findViewById<ToggleButton>(R.id.btnToggleFavorite)

        toggleButton?.setOnCheckedChangeListener { _, isChecked ->
           // Listen for when the ToggleButton is tapped
            if (isChecked) {
                // First check to see if the card is already a favorite.
                if(_searchPresenter.isFavorite(entityName)) {
                    // Tell user it is already a favorite
                    Toast.makeText(activity?.applicationContext, "This Card is already a favorite", Toast.LENGTH_SHORT)
                        .show()
                    // Turn the button off
                    toggleButton.toggle()
                } else { // If it is not already a part of the favorite list, add it.
                    _searchPresenter.addFavorite(entityName)
                    Log.i("Favorite Button", toggleButton.isChecked.toString())
                    // tell the user it has been added to the favorite list
                    Toast.makeText(activity?.applicationContext, "Added to Favorites", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                // If the button was tapped by mistake, they can tap it again to remove the card.
                _searchPresenter.delFavorite(entityName)
                // Tell user it has been removed from the list
                Toast.makeText(activity?.applicationContext, "Removed from Favorites", Toast.LENGTH_SHORT)
                    .show()
            }
        }



    }

    companion object {
        fun dpToPixels(dp: Int, context: SearchFragment): Float {
            return dp * context.resources.displayMetrics.density
        }


    }

    override fun drawCard(entityMap : kotlin.collections.Map<String, Any?>, entityName: String) {
        if (entityMap.isNotEmpty()) {
            _cardAdapter!!.addCardItem(entityMap)
            _cardAdapter!!.notifyDataSetChanged()
        }
    }

    override fun showAdded(name: String) {
        Toast.makeText(activity?.applicationContext, "$name added to Favorites", Toast.LENGTH_SHORT)
            .show()
    }

    override fun showDeleted(name: String) {
        Toast.makeText(activity?.applicationContext, "$name removed from Favorites", Toast.LENGTH_SHORT)
            .show()
    }

}