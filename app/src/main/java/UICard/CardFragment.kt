package UICard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.c19.R

/**
 * Creating a card as a fragment, and tying in the card layout to it.
 * @author Chase Moses
 */
class CardFragment : Fragment() {

    private var _cardView : CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(
            R.layout.card,
            container,
            false)

        _cardView = view.findViewById<View>(R.id.cardViewer) as CardView
        _cardView!!.maxCardElevation = _cardView!!.cardElevation * 8
        return view
    }

    fun getCardView() : CardView? {return _cardView}
}