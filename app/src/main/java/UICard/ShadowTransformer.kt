package UICard

import android.view.View
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager

/**
 * This class is designed to blow up the card when it is being viewed.
 * It will shrink as the user scrolls past it and onto another card.
 */

class ShadowTransformer(viewPager: ViewPager?, adapter: CardAdapter) :
    ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private val _viewPager : ViewPager
    private val _adapter : CardAdapter
    private var _lastOffset = 0f
    private var _scalingEnabled = false

    fun enableScaling(enable: Boolean) {
        // Shrinking main card
        if(_scalingEnabled && !enable) {
            val currentCard : CardView? = _adapter.getCardViewAt(_viewPager.currentItem)

            if (currentCard != null) {
                currentCard.animate().scaleY(1F)
                currentCard.animate().scaleX(1F)
            }
        } else if (!_scalingEnabled && enable) {
            // Growing main card
            val currentCard : CardView? = _adapter.getCardViewAt(_viewPager.currentItem)

            if(currentCard != null) {
                currentCard.animate().scaleY(1.1f)
                currentCard.animate().scaleX(1.1f)
            }
        }
        _scalingEnabled = enable
    }

    override fun transformPage(page: View, position: Float) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation = _adapter.getBaseElevation()
        val realOffset: Float
        val goingLeft =_lastOffset > positionOffset

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }

        // Make sure no crash happens when the user scrolls past the limit
        if(nextPosition > _adapter.getCount() - 1
            || realCurrentPosition > _adapter.getCount() - 1) {
            return
        }
        val nextCard : CardView? = _adapter.getCardViewAt(nextPosition)

        // Sometimes people scroll so fast that the card was either destroyed already or not yet created. Check for that.
        if (nextCard != null) {
            if(_scalingEnabled) {
                nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
                nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()
            }
            nextCard.cardElevation = baseElevation + (baseElevation * (8 - 1) * realOffset)
        }
        _lastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}

    init {
        _viewPager = viewPager!!
        viewPager.addOnPageChangeListener(this)
        _adapter = adapter
    }

    }