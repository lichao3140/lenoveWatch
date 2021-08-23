package cn.ycgo.base.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import cn.ycgo.base.common.R

/**
 *Author:Kgstt
 *Time: 2020/11/23
 */
class CornerImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    companion object {
        // 默认圆角大小
        private const val DEFAULT_CORNER = 0f
    }

    //圆角大小，默认为10
    var corner = DEFAULT_CORNER
    private var cornerBackgroundColor = Color.parseColor("#FBFBFC")

    fun setCornerDp(corner: Float) {
        this.corner = corner
        invalidate()
    }

    var isCircle = false
    fun setIsCircle(isCircle: Boolean) {
        this.isCircle = isCircle
        if(isCircle){
            scaleType = ScaleType.CENTER_CROP
        }
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isCircle) {
            scaleType = ScaleType.CENTER_CROP
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (isCircle) {
            scaleType = ScaleType.CENTER_CROP
            val path = Path()
            path.addCircle(width / 2f, height / 2f, height.coerceAtLeast(width) / 2f, Path.Direction.CW)
            canvas.clipPath(path)
        } else if (corner != 0f) {
            val path = Path()
            path.moveTo(corner, 0f)
            path.lineTo((width - corner), 0f)
            path.quadTo(width.toFloat(), 0f, width.toFloat(), corner)
            path.lineTo(width.toFloat(), (height - corner))
            path.quadTo(width.toFloat(), height.toFloat(), (width - corner), height.toFloat())
            path.lineTo(corner, height.toFloat())
            path.quadTo(0f, height.toFloat(), 0f, (height - corner))
            path.lineTo(0f, corner)
            path.quadTo(0f, 0f, corner, 0f)
            canvas.clipPath(path)
        }
        canvas.drawColor(cornerBackgroundColor)
        super.onDraw(canvas)
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerImageView)
        corner = typedArray.getDimension(R.styleable.CornerImageView_corner, DEFAULT_CORNER)
        isCircle = typedArray.getBoolean(R.styleable.CornerImageView_isCircle, isCircle)
        cornerBackgroundColor = typedArray.getColor(
            R.styleable.CornerRoundView_corner_background_color,
            Color.parseColor("#FBFBFC")
        )
        typedArray.recycle()
    }

}