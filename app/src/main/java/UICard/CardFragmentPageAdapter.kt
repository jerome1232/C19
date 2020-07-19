package UICard

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Adapter for the CardFragments
 *
 * @author Chase Moses
 */

class CardFragmentPageAdapter(fm : FragmentManager?, baseElevation : Float)
    : FragmentStatePagerAdapter(fm!! , BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), CardAdapter {

    private val _fragments : MutableList<CardFragment>
    private val mBaseElevation : Float


    override fun getBaseElevation(): Float {
        return mBaseElevation
    }

    override fun getCardViewAt(position: Int): CardView? {
        return _fragments[position].getCardView()
    }

    override fun getCount(): Int {
        return _fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return _fragments.get(position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)
        _fragments[position] = fragment as CardFragment
        return fragment
    }


    fun addCardFragment(fragment: CardFragment) {
        _fragments.add(fragment)
    }

    init {
        _fragments = ArrayList()
        mBaseElevation = baseElevation
        for(i in 0..4) {
            addCardFragment(CardFragment())
        }
    }

}