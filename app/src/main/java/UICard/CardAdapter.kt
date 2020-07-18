package UICard

import androidx.cardview.widget.CardView

/**
 * Sets up the different Classes that will need these implemented.
 * @author Chase Moses
 */
interface CardAdapter {
    fun getBaseElevation(): Float

    fun getCardViewAt(position: Int): CardView?

    fun getCount(): Int
}