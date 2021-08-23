package cn.ycgo.base.common.widget.banner.decoration
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.common.widget.banner.annotation.Align.BOTTOM
import cn.ycgo.base.common.widget.banner.annotation.Align.LEFT
import cn.ycgo.base.common.widget.banner.annotation.Align.RIGHT
import cn.ycgo.base.common.widget.banner.annotation.Align.TOP
import cn.ycgo.base.common.widget.banner.annotation.IndicatorAlign

import kotlin.math.max

@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class DotIndicatorDecoration(
    //是否是无限循环
    private val isInfinite: Boolean = true,
    //指示器位置，可以用 ALIGN_BOTTOM or ALIGN_RIGHT 来表示右下
    @IndicatorAlign
    private var align: Int = BOTTOM,
    //选中/非选中圆点尺寸和颜色
    private var selectedWidthSize: Int = 16.dpToPxOffset(),
    private var pointSize: Int = 8.dpToPxOffset(),
    private var selectedColor: Int = Color.RED,
    private var unselectedColor: Int = Color.WHITE,
    //圆点之间的间距
    private var space: Int = 6.dpToPxOffset(),
    //竖向边距
    private var verticalMargin: Int = 16.dpToPxOffset(),
    //横向边距
    private var horizontalMargin: Int = 16.dpToPxOffset()
) : ItemDecoration() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * RecyclerView 的每次滚动都会调用
     */
    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        //banner宽高
        val width = parent.measuredWidth
        val height = parent.measuredHeight
        //条目个数
        val itemCount = if (isInfinite) {
            layoutManager!!.itemCount - 2
        } else {
            layoutManager!!.itemCount
        }
        //当前显示的所有条目
        val childCount = layoutManager.childCount
        val centerX = width / 2
        val centerY = height / 2
        val rect = Rect()
        //中心位置的条目position
        var centerItemPosition = 0
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            layoutManager.getDecoratedBoundsWithMargins(child!!, rect)
            if (rect.contains(centerX, centerY)) {
                centerItemPosition = layoutManager.getPosition(child)
                break
            }
        }
        if (isInfinite) {
            centerItemPosition = fixPosition(centerItemPosition, layoutManager.itemCount)
        }
        //指示器总体宽度
        val indicatorWidth = pointSize * itemCount +
                space * (itemCount - 1) + selectedWidthSize - pointSize
        //指示器位置
        var indicatorLeft = (width - indicatorWidth) / 2
        if (align and LEFT == LEFT) {
            indicatorLeft = horizontalMargin
        }
        if (align and RIGHT == RIGHT) {
            indicatorLeft = width - indicatorWidth - horizontalMargin
        }
        var indicatorTop = (height - pointSize) / 2
        if (align and TOP == TOP) {
            indicatorTop = verticalMargin
        }
        if (align and BOTTOM == BOTTOM) {
            indicatorTop = height - max(pointSize, selectedWidthSize) - verticalMargin
        }
        //绘制指示器
        for (i in 0 until itemCount) {
            var r = pointSize
            if (centerItemPosition == i) { //选中条目指示器颜色
                mPaint.color = selectedColor
                r = pointSize
                val left = i * (pointSize + space) + indicatorLeft
                val cx = left + pointSize / 2f
                val cy = indicatorTop
                c.drawRoundRect(RectF(cx,cy.toFloat(),cx+selectedWidthSize,cy+r.toFloat()),r.toFloat(),r.toFloat(),mPaint)
            } else {
                mPaint.color = unselectedColor
                val left = if(centerItemPosition<i) {
                    i * (pointSize + space) + indicatorLeft+selectedWidthSize
                }else{
                    i * (pointSize + space) + indicatorLeft
                }
                val cx = left + pointSize / 2
                val cy = indicatorTop + pointSize / 2
                c.drawCircle(cx.toFloat(), cy.toFloat(), r / 2f, mPaint)
            }
        }
    }

    //修复无限循环的banner条目位置
    private fun fixPosition(position: Int, count: Int): Int {
        var realPosition = position
        if (count > 1) {
            if (position > 0 && position < count - 1) {
                realPosition = position - 1
            } else if (position == 0) {
                realPosition = count - 3
            } else if (position == count - 1) {
                realPosition = 0
            }
        }
        return realPosition
    }

    fun setSelectedWidthSize(selectedWidthSize: Int) {
        this.selectedWidthSize = selectedWidthSize.dpToPxOffset()
    }

    fun setPointSize(pointSize: Int) {
        this.pointSize = pointSize.dpToPxOffset()
    }

    fun setSelectedColor(selectedColor: Int) {
        this.selectedColor = selectedColor
    }

    fun setUnselectedColor(unselectedColor: Int) {
        this.unselectedColor = unselectedColor
    }

    fun setSpace(space: Int) {
        this.space = space.dpToPxOffset()
    }

    fun setVerticalPadding(verticalPadding: Int) {
        this.verticalMargin = verticalPadding.dpToPxOffset()
    }

    fun setHorizontalPadding(horizontalPadding: Int) {
        this.horizontalMargin = horizontalPadding.dpToPxOffset()
    }

    fun setAlign(@IndicatorAlign align: Int) {
        this.align = align
    }

}