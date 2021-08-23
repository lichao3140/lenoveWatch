package cn.ycgo.base.common.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import cn.ycgo.base.common.R


/**
 *Author:Kgstt
 *Time: 2020/11/23
 */
class CornerFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private var topLeftCorner = 0f
    private var topRightCorner = 0f
    private var bottomLeftCorner = 0f
    private var bottomRightCorner = 0f
    private val strokePaint: Paint
    private val roundPaint: Paint
    private val imagePaint: Paint

    private var strokeSize = 0f
    private var strokeColor = Color.TRANSPARENT

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CornerFrameLayout)
            val corner = ta.getDimension(R.styleable.CornerFrameLayout_corner, 0f)
            topLeftCorner = ta.getDimension(R.styleable.CornerFrameLayout_corner_lt, corner)
            topRightCorner = ta.getDimension(R.styleable.CornerFrameLayout_corner_rt, corner)
            bottomLeftCorner = ta.getDimension(R.styleable.CornerFrameLayout_corner_lb, corner)
            bottomRightCorner = ta.getDimension(R.styleable.CornerFrameLayout_corner_rb, corner)
            strokeSize = ta.getDimension(R.styleable.CornerFrameLayout_corner_stroke_size, strokeSize)
            strokeColor = ta.getColor(R.styleable.CornerFrameLayout_corner_stroke_color, strokeColor)
            ta.recycle()
        }

        roundPaint = Paint()
        roundPaint.color = Color.WHITE
        roundPaint.isAntiAlias = true
        roundPaint.style = Paint.Style.FILL
        roundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        imagePaint = Paint()
        imagePaint.xfermode = null

        strokePaint = Paint()
        strokePaint.color = strokeColor
        strokePaint.isAntiAlias = true
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeSize
    }

    fun setStrokeColor(@ColorInt color: Int) {
        strokeColor = color
        strokePaint.color = strokeColor
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(
            RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat()),
            imagePaint,
            Canvas.ALL_SAVE_FLAG
        )
        if (strokeSize > 0 && strokeColor != Color.TRANSPARENT) {
            drawStrokeTopLeft(canvas)
            drawStrokeTopRight(canvas)
            drawStrokeBottomLeft(canvas)
            drawStrokeBottomRight(canvas)
            drawStrokeFrame(canvas)
        }
        super.dispatchDraw(canvas)
        drawTopLeft(canvas)
        drawTopRight(canvas)
        drawBottomLeft(canvas)
        drawBottomRight(canvas)
        canvas.restore()
    }

    private fun drawTopLeft(canvas: Canvas) {
        if (topLeftCorner > 0) {
            val path = Path()
            path.moveTo(0f, topLeftCorner)
            path.lineTo(0f, 0f)
            path.lineTo(topLeftCorner, 0f)
            path.arcTo(RectF(0F, 0F, topLeftCorner * 2, topLeftCorner * 2), -90f, -90f)
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawTopRight(canvas: Canvas) {
        if (topRightCorner > 0) {
            val width = width
            val path = Path()
            path.moveTo(width - topRightCorner, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), topRightCorner)
            path.arcTo(
                RectF(width - 2 * topRightCorner, 0F, width.toFloat(), topRightCorner * 2),
                0f,
                -90f
            )
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawBottomLeft(canvas: Canvas) {
        if (bottomLeftCorner > 0) {
            val height = height
            val path = Path()
            path.moveTo(0f, height - bottomLeftCorner)
            path.lineTo(0f, height.toFloat())
            path.lineTo(bottomLeftCorner, height.toFloat())
            path.arcTo(
                RectF(
                    0F,
                    height - 2 * bottomLeftCorner,
                    bottomLeftCorner * 2,
                    height.toFloat()
                ), 90f, 90f
            )
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawBottomRight(canvas: Canvas) {
        if (bottomRightCorner > 0) {
            val height = height
            val width = width
            val path = Path()
            path.moveTo(width - bottomRightCorner, height.toFloat())
            path.lineTo(width.toFloat(), height.toFloat())
            path.lineTo(width.toFloat(), height - bottomRightCorner)
            path.arcTo(
                RectF(
                    width - 2 * bottomRightCorner,
                    height - 2 * bottomRightCorner,
                    width.toFloat(),
                    height.toFloat()
                ), 0f, 90f
            )
            path.close()
            canvas.drawPath(path, roundPaint)
        }
    }

    private fun drawStrokeTopLeft(canvas: Canvas) {
        if (topLeftCorner > 0 && strokeSize > 0) {
            val oval = RectF(
                0F + (strokeSize / 2),
                0F + (strokeSize / 2),
                topLeftCorner * 2,
                topLeftCorner * 2
            )
            canvas.drawArc(oval, -90f, -90f, false, strokePaint)
        }
    }

    private fun drawStrokeTopRight(canvas: Canvas) {
        if (topRightCorner > 0 && strokeSize > 0) {
            val oval = RectF(
                width - 2 * topRightCorner,
                0F + (strokeSize / 2),
                width.toFloat() - (strokeSize / 2),
                topRightCorner * 2
            )
            canvas.drawArc(oval, 0f, -90f, false, strokePaint)
        }
    }

    private fun drawStrokeBottomLeft(canvas: Canvas) {
        if (bottomLeftCorner > 0 && strokeSize > 0) {
            val oval = RectF(
                0F + (strokeSize / 2),
                height - 2 * bottomLeftCorner,
                bottomLeftCorner * 2,
                height.toFloat() - (strokeSize / 2)
            )
            canvas.drawArc(oval, -180f, -90f, false, strokePaint)
        }
    }

    private fun drawStrokeBottomRight(canvas: Canvas) {
        if (bottomRightCorner > 0 && strokeSize > 0) {
            val oval = RectF(
                width - 2 * bottomRightCorner,
                height - 2 * bottomRightCorner,
                width.toFloat() - (strokeSize / 2),
                height.toFloat() - (strokeSize / 2)
            )
            canvas.drawArc(oval, 0f, 90f, false, strokePaint)
        }
    }

    private fun drawStrokeFrame(canvas: Canvas) {
        if (strokeSize > 0) {
            canvas.drawRect((strokeSize / 2), (strokeSize / 2), width.toFloat() - (strokeSize / 2), height.toFloat() - (strokeSize / 2), strokePaint)
        }
    }


}