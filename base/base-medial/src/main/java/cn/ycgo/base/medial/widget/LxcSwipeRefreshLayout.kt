package cn.ycgo.base.medial.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.R
import kotlin.math.abs

/**
 *Author:Kgstt
 *Time: 2021/1/4
 */
class LxcSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        setColorSchemeColors(ContextProvider.getColor(R.color.color_theme), ContextProvider.getColor(R.color.color_gray_95))
    }

    // 上一次触摸时的X坐标
    private var mPrevX = 0f
    private var mTouchSlop = 0

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPrevX = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = abs(eventX - mPrevX)
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false
                }

            }
        }
        return super.onInterceptHoverEvent(event)
    }


}