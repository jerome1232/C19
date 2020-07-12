package UICard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.c19.R
import org.jetbrains.annotations.NotNull

class CardPageAdapter() : PagerAdapter(), CardAdapter {

    private var cards : MutableList<Map<String, Any?>>
    private var cardViews : MutableList<CardView?>
    private var mBaseElevation = 0f


    fun addCardItem(cardData : Map<String, Any?>) {
        cards.add(cardData)
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

    private fun bind(cardInfo : Map<String, Any?>, view : View) {

        val title = view.findViewById<TextView>(R.id.cardTitle)
        val names = listOf<TextView>(
            view.findViewById(R.id.totalCases),
            view.findViewById(R.id.totalCases2),
            view.findViewById(R.id.totalCases3),
            view.findViewById(R.id.totalCases4)
        )
        val values = listOf<TextView>(
            view.findViewById(R.id.totalCaseNum),
            view.findViewById(R.id.activeCaseNum),
            view.findViewById(R.id.recoveredNum),
            view.findViewById(R.id.deathsNum)
        )

        val nameValuePairs = (names zip values)
        val cardInfoMutable = cardInfo.toMutableMap()
        title.text = cardInfoMutable["title"].toString()
        cardInfoMutable.remove("title")
        cardInfoMutable.keys.forEachIndexed { index, key ->
            if (index < nameValuePairs.size) {
                nameValuePairs[index].first.text = key
                nameValuePairs[index].second.text = cardInfo[key].toString()
            }
        }
    }

    init {
        cards = ArrayList()
        cardViews = ArrayList<CardView?>()
    }

}