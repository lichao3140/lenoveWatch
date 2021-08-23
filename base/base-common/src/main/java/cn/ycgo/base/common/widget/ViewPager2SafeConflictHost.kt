package cn.ycgo.base.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import java.util.*

/**
 * Author:Kgstt
 * Time: 2020/11/27
 * 解决ViewPager2嵌套ViewPager2滑动冲突问题
 * 嵌套在子ViewPager2外面
 */
class ViewPager2SafeConflictHost : FrameLayout {
    private var touchSlop: Int
    private var initialX = 0f
    private var initialY = 0f
    private var findViewCache: HashMap<*, *>? = null
    private val parentViewPager: ViewPager2?
        get() {
            var var10000 = this.parent
            if (var10000 !is View) {
                var10000 = null
            }
            var v: View?
            v = var10000 as View
            while (v != null && v !is ViewPager2) {
                var10000 = v.parent
                if (var10000 !is View) {
                    var10000 = null
                }
                    v = var10000 as View
            }
            var var2: View? = v
            if (v !is ViewPager2) {
                var2 = null
            }
            return var2 as ViewPager2
        }
    private val child: View?
        private get() = if (this.childCount > 0) getChildAt(0) else null

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val var5 = false
        val direction = -Math.signum(delta).toInt()
        val var10000: View?
        var var6 = false
        when (orientation) {
            0 -> {
                var10000 = child
                var6 = var10000?.canScrollHorizontally(direction) ?: false
            }
            1 -> {
                var10000 = child
                var6 = var10000?.canScrollVertically(direction) ?: false
            }
            else -> {
            }
        }
        return var6
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val var10000 = parentViewPager
        if (var10000 != null) {
            val orientation = var10000.orientation
            if (canChildScroll(orientation, -1.0f) || canChildScroll(orientation, 1.0f)) {
                if (e.action == 0) {
                    initialX = e.x
                    initialY = e.y
                    this.parent.requestDisallowInterceptTouchEvent(true)
                } else if (e.action == 2) {
                    val dx = e.x - initialX
                    val dy = e.y - initialY
                    val isVpHorizontal = orientation == 0
                    val var8 = false
                    val scaledDx = Math.abs(dx) * if (isVpHorizontal) 0.5f else 1.0f
                    val var9 = false
                    val scaledDy = Math.abs(dy) * if (isVpHorizontal) 1.0f else 0.5f
                    if (scaledDx > touchSlop.toFloat() || scaledDy > touchSlop.toFloat()) {
                        if (isVpHorizontal == scaledDy > scaledDx) {
                            this.parent.requestDisallowInterceptTouchEvent(false)
                        } else if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                            this.parent.requestDisallowInterceptTouchEvent(true)
                        } else {
                            this.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                }
            }
        }
    }

    constructor(context: Context?) : super(context!!) {
        val var10001 = ViewConfiguration.get(context)
        touchSlop = var10001.scaledTouchSlop
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        val var10001 = ViewConfiguration.get(context)
        touchSlop = var10001.scaledTouchSlop
    }

    fun findViewCache(var1: Int): View? {
        if (findViewCache == null) {
            findViewCache = HashMap<Any?, Any?>()
        }
        var var2 = findViewCache!![var1] as View?
        if (var2 == null) {
            var2 = findViewById(var1)
            findViewCache?.put(var1 as Nothing, var2 as Nothing)
        }
        return var2
    }

    fun findViewCache() {
        if (findViewCache != null) {
            findViewCache?.clear()
        }
    }
}