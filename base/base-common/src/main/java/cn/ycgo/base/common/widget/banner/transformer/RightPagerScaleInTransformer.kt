package cn.ycgo.base.common.widget.banner.transformer
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class RightPagerScaleInTransformer(
    @param:Px private val margin: Int,
    private val scale: Float = 1f
) :
    ViewPager2.PageTransformer {
    override fun transformPage(
        view: View,
        position: Float
    ) {
        val offset = position * margin
        if (position < -1) {
//            view.alpha = 0f
        } else if (position <= 0) {
            view.alpha = 1f
            view.translationX = 0f
            view.scaleX = 1f
            view.scaleY = 1f
        } else if (position <= 1) {
            view.alpha = 1f
            view.translationX = -offset
            view.scaleY = 1 - scale * abs(position)
        } else {
//            view.alpha = 0f
        }
    }

    private fun requireViewPager(page: View): ViewPager2 {
        val parent = page.parent
        val parentParent = parent.parent
        if (parent is RecyclerView && parentParent is ViewPager2) {
            return parentParent
        }
        throw IllegalStateException(
            "Expected the page view to be managed by a ViewPager2 instance."
        )
    }

}