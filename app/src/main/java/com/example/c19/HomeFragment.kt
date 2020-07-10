package com.example.c19

import UICard.CardFragmentPageAdapter
import UICard.CardModel
import UICard.CardPageAdapter
import UICard.ShadowTransformer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.c19.model.CovidManager
//import com.example.c19.model.CovidCountryManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class HomeFragment : Fragment() {

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

        _viewPager = view.findViewById(R.id.cardViewPager)
        _cardAdapter = CardPageAdapter()

        _cardAdapter!!.addCardItem(
            CardModel(
                "Global",
                9689411,
                3898161,
                5249782,
                488959
            )
        )
        _cardAdapter!!.addCardItem(
            CardModel(
                "Test Card",
                300,
                150,
                140,
                10
            )
        )
        _cardAdapter!!.addCardItem(
            CardModel(
                "United States",
                2540324,
                1634180,
                778880,
                127264
            )
        )

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

    /**
     * TODO REMOVE ME AT WILL
     *
     * I'm just here for testing
     *
     * I'm not sure why but it seems this function
     * needed to exist in both the fragment and
     * the MainActivity? I didn't care to research it, I just wanted
     * it to work for testing.
     * @param view
     */
    fun dataCountryTest(view: View) {
        doAsync {
            val manager = CovidManager()
            val country = manager.getEntity("bolivia")
            uiThread {
                Log.i("dataTest", country.toString())
            }
        }
    }

    companion object {
        fun dpToPixels(dp: Int, context: HomeFragment): Float {
            return dp * context.resources.displayMetrics.density
        }


    }
}