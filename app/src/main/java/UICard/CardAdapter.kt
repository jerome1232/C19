package UICard

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.c19.R
import org.jetbrains.annotations.NotNull

class CardAdapter(cards: List<CardModel>, context : Activity ) : PagerAdapter() {

    private var cards : List<CardModel> = cards
    private var context = context
    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return cards.size
    }

    override fun isViewFromObject(view: View, item: Any): Boolean {
        return view == item
    }

    @NotNull
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater= LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(R.layout.card, container, false)

        val cardTitle : TextView = view.findViewById(R.id.cardTitle)
        val numTotalCase : TextView = view.findViewById(R.id.totalCaseNum)
        val numActiveCase : TextView = view.findViewById(R.id.activeCaseNum)
        val numRecover : TextView = view.findViewById(R.id.recoveredNum)
        val numDeath : TextView = view.findViewById(R.id.deathsNum)

        cardTitle.setText(cards.get(position).title)
        numTotalCase.setText(cards.get(position).numTotal)
        numActiveCase.setText(cards.get(position).numActive)
        numRecover.setText(cards.get(position).numRecover)
        numDeath.setText(cards.get(position).numDeaths)

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as? View)
    }

}