package UICard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.c19.R
import com.example.c19.presenter.FavoritesPresenter
import com.example.c19.presenter.HomePresenter
import com.example.c19.presenter.SearchPresenter
import org.jetbrains.annotations.NotNull

class CardPageAdapter(favoritesPresenter: FavoritesPresenter) : PagerAdapter(), CardAdapter {

    private val _favoritesPresenter = favoritesPresenter
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
            view.findViewById(R.id.name1),
            view.findViewById(R.id.name2),
            view.findViewById(R.id.name3),
            view.findViewById(R.id.name4),
            view.findViewById(R.id.name5),
            view.findViewById(R.id.name6)
        )
        val values = listOf<TextView>(
            view.findViewById(R.id.value1),
            view.findViewById(R.id.value2),
            view.findViewById(R.id.value3),
            view.findViewById(R.id.value4),
            view.findViewById(R.id.value5),
            view.findViewById(R.id.value6)
        )
        val dividers = listOf<View>(
            view.findViewById(R.id.body_divider),
            view.findViewById(R.id.body_divider2),
            view.findViewById(R.id.body_divider3),
            view.findViewById(R.id.divider4),
            view.findViewById(R.id.divider5),
            view.findViewById(R.id.divider6)
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

        // Hide extra rows
        val cardLayout = view.findViewById<ConstraintLayout>(R.id.CardLayout)
        for (i in cardInfoMutable.count()..(nameValuePairs.size - 1)) {
            names[i].visibility = View.GONE
            values[i].visibility = View.GONE
            dividers[i].visibility = View.GONE
        }

        val toggleButton = cardLayout.findViewById<ToggleButton>(R.id.btnToggleFavorite)
        Log.i("Card", _favoritesPresenter.isFavorite(title.text.toString()).toString())
        if ( (_favoritesPresenter is HomePresenter) &&
            _favoritesPresenter.isFavorite(title.text.toString())) toggleButton.isChecked = true
        toggleButton.setOnClickListener {
            if (toggleButton.isChecked) {
                _favoritesPresenter.addFavorite(title.text.toString())
            } else {
                _favoritesPresenter.delFavorite(title.text.toString())
            }

        }

    }

    init {
        cards = ArrayList()
        cardViews = ArrayList<CardView?>()
    }

}