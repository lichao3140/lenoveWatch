package cn.ycgo.base.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import cn.ycgo.base.common.R
import cn.ycgo.base.common.utils.dpToPx


/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
class CornerRoundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : View(context, attrs, defStyle) {
    private var corner = 0f
    private var stroke_size = 0f
    private var background_color = 0
    private var stroke_color = 0

    private val strokePaint: Paint
    private val backgroundPaint: Paint

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CornerRoundView)
            corner = ta.getDimension(R.styleable.CornerRoundView_corner, 1f)
            stroke_size = ta.getDimension(R.styleable.CornerRoundView_corner_stroke_size, 0f)
            background_color = ta.getColor(
                R.styleable.CornerRoundView_corner_background_color,
                Color.TRANSPARENT
            )

            stroke_color =
                ta.getColor(
                    R.styleable.CornerRoundView_corner_stroke_color,
                    Color.TRANSPARENT
                )
            ta.recycle()
        }
        strokePaint = Paint()
        strokePaint.color = stroke_color
        strokePaint.isAntiAlias = true
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = stroke_size

        backgroundPaint = Paint()
        backgroundPaint.color = background_color
        backgroundPaint.isAntiAlias = true
        backgroundPaint.style = Paint.Style.FILL
    }

    fun setRoundStrokeColor(@ColorInt color: Int) {
        stroke_color = color
        strokePaint.color = stroke_color
        invalidate()
    }

    fun setRoundBackgroundColor(@ColorInt color: Int) {
        background_color = color
        backgroundPaint.color = background_color
        invalidate()
    }

    fun setRoundStrokeSize(dpSize: Float) {
        stroke_size = dpSize.dpToPx()
        strokePaint.strokeWidth = stroke_size
        invalidate()
    }

    fun setRoundCorner(dpSize: Float) {
        corner = dpSize.dpToPx()
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            val oval = RectF(
                paddingLeft + stroke_size,
                paddingTop + stroke_size,
                width.toFloat() - stroke_size - paddingRight,
                height.toFloat() - stroke_size - paddingBottom
            ) // 设置个新的长方形
            if (background_color != Color.TRANSPARENT) {
                //画圆角矩形
                drawRoundRect(
                    oval,
                    corner,
                    corner,
                    backgroundPaint
                ) //第二个参数是x半径，第三个参数是y半
            }
            if (stroke_size != 0f && stroke_color != Color.TRANSPARENT) {
                //画边框
                drawRoundRect(
                    oval,
                    corner,
                    corner,
                    strokePaint
                ) //第二个参数是x半径，第三个参数是y半
            }
        }

    }

}