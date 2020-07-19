package com.example.c19

import UICard.CardFragmentPageAdapter
import UICard.CardPageAdapter
import UICard.ShadowTransformer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
import com.example.c19.presenter.HomePresenterImpl
import com.example.c19.view.HomeView
import kotlinx.android.synthetic.main.card.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeFragment : Fragment(), HomeView {

    // TODO: Make favorite button always on for those cards in the favorite list.

    private val _homePresenter = HomePresenterImpl(CovidManager(), this)
    private var _viewPager: ViewPager? = null
    private var _cardAdapter: CardPageAdapter? = null
    private var _CardShadowTransformer: ShadowTransformer? = null
    private var _FragmentCardAdapter: CardFragmentPageAdapter? = null
    private var _FragmentCardShadowTransformer: ShadowTransformer? = null

    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.home)

        _viewPager = view.findViewById(R.id.cardViewPager)
        _cardAdapter = CardPageAdapter(_homePresenter)

        _homePresenter.getFavorites()
        _FragmentCardAdapter = CardFragmentPageAdapter(
            fragmentManager,
            dpToPixels(2, this)
        )
        _CardShadowTransformer = ShadowTransformer(_viewPager, _cardAdapter!!)
        _FragmentCardShadowTransformer = ShadowTransformer(_viewPager, _FragmentCardAdapter!!)
        _viewPager?.adapter = _cardAdapter
        _viewPager?.setPageTransformer(false, _CardShadowTransformer)
        _viewPager?.offscreenPageLimit = 3




        return view
    }


    companion object {
        fun dpToPixels(dp: Int, context: HomeFragment): Float {
            return dp * context.resources.displayMetrics.density
        }


    }

    override fun drawFavorites(favorites: List<Map<String, Any?>>) {
        favorites.forEach {
                favorite ->  _cardAdapter!!.addCardItem(favorite)
        }
        _cardAdapter!!.notifyDataSetChanged()
    }

    override fun removeFavorite(name: String) {
        _cardAdapter!!.removeCardItem(name)
        _cardAdapter!!.notifyDataSetChanged()
        Toast.makeText(activity?.applicationContext, "$name removed from Favorites", Toast.LENGTH_SHORT)
            .show()
    }
}