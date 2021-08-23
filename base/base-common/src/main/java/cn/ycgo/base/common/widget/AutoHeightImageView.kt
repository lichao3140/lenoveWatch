package cn.ycgo.base.common.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil


/**
 *Author:Kgstt
 *Time: 2021/1/20
 */
class AutoHeightImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    var maxImageHeight = -1

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        adjustViewBounds = true
        scaleType = ScaleType.FIT_XY
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable
        if (d != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height =
                ceil((width.toFloat() * d.intrinsicHeight.toDouble() / d.intrinsicWidth.toDouble())).toInt()
            if (maxImageHeight != -1 && height > maxImageHeight) {
                height = maxImageHeight
                scaleType = ScaleType.CENTER_CROP
            } else {
                scaleType = ScaleType.FIT_XY
            }
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}