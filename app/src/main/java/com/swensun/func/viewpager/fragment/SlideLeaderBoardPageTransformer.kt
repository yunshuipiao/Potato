package   com.swensun.func.viewpager.fragment

import android.view.View
import androidx.viewpager.widget.ViewPager

class SlideLeaderBoardPageTransformer : ViewPager.PageTransformer {

    val MAX_SCALE = 1.0f
    val MIN_SCALE = 0.7f

    override fun transformPage(page: View, p: Float) {

        var newPosition = p
        if (newPosition < -1) {
            newPosition = -1f
        } else if (newPosition > 1) {
            newPosition = 1f
        }

        val tempScale = if (newPosition < 0) 1 + newPosition else 1 - newPosition
        val slope: Float = (MAX_SCALE - MIN_SCALE) / 1
        val scaleValue: Float = MIN_SCALE + tempScale * slope
        page.scaleX = scaleValue
        page.scaleY = scaleValue
        page.translationX = (1 - scaleValue) * -1200
    }
}