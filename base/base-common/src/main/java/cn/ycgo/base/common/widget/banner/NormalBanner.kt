package cn.ycgo.base.common.widget.banner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@SuppressLint("NewApi")
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class NormalBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewPager2Banner(context, attrs, defStyleAttr, defStyleRes) {
    override fun initView() {
        mViewPager2.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        mViewPager2.offscreenPageLimit = 1
        mViewPager2.registerOnPageChangeCallback(PageFixCallback())
        addView(mViewPager2)
    }
}