package UICard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.c19.R
import org.jetbrains.annotations.NotNull
import kotlin.properties.Delegates

class CardPageAdapter() : PagerAdapter(), CardAdapter {

    private var cards : MutableList<CardModel>
    private var cardViews : MutableList<CardView?>
    private var mBaseElevation = 0f


    fun addCardItem(card : CardModel) {
        cards.add(card)
        cardViews.add(null)

    }

    override fun getBaseElevation(): Float {
        return mBaseElevation
    }

    override fun getCardViewAt(position: Int): CardView? {
        return cardViews[position]
    }

    override fun getCount(): Int {
        return cards.size
    }

    override fun isViewFromObject(view: View, item: Any): Boolean {
        return view == item
    }

    @NotNull
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view : View = LayoutInflater
            .from(container.context)
            .inflate(R.layout.card, container, false)

        container.addView(view)
        bind(cards[position], view)

        val cardView = view.findViewById<CardView>(R.id.cardViewer) as CardView

        if(mBaseElevation == 0f) {
            mBaseElevation = cardView.cardElevation

        }

        cardView.maxCardElevation = mBaseElevation * 8
        cardViews[position] = cardView
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
        cardViews[position] = null
    }

    private fun bind(card : CardModel, view : View) {


        val cardTitle : TextView = view.findViewById(R.id.cardTitle)
        val numTotalCase : TextView = view.findViewById(R.id.totalCaseNum)
        val numActiveCase : TextView = view.findViewById(R.id.activeCaseNum)
        val numRecover : TextView = view.findViewById(R.id.recoveredNum)
        val numDeath : TextView = view.findViewById(R.id.deathsNum)

        cardTitle.text = card.title
        numTotalCase.text = String.format(card.numTotal.toString())
        numActiveCase.text = String.format(card.numActive.toString())
        numRecover.text = String.format(card.numRecover.toString())
        numDeath.text = String.format(card.numDeaths.toString())
    }

    init {
        cards = ArrayList()
        cardViews = ArrayList<CardView?>()
    }

}