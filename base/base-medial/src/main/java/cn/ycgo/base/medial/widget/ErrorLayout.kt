package cn.ycgo.base.medial.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.LayoutErrorBinding

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 错误页
 */
class ErrorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding: LayoutErrorBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        viewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_error, this, true)
    }

    fun setTvMessage(@StringRes messageId: Int) {
        setTvMessage(context.getString(messageId))
    }

    fun setTvMessage(messageString: String) {
        viewBinding.tvErrorTip.text = messageString
    }

    fun setIvError(@DrawableRes errorDrawableRes: Int) {
        ContextCompat.getDrawable(context, errorDrawableRes)?.run {
            setIvError(this)
        }
    }

    fun setIvError(errorDrawable: Drawable?) {
        viewBinding.ivError.setImageDrawable(errorDrawable)
    }

    fun setBtnRefresh(@StringRes textId: Int = R.string.text_error_layout_refresh, block: () -> Unit) {
        setBtnRefresh(context.getString(textId), block)
    }

    fun setBtnRefresh(text: String, block: () -> Unit) {
        viewBinding.btnRefresh.text = text
        viewBinding.btnRefresh.setOnClickListener {
            block.invoke()
        }
    }

    fun hideRefreshBtn(isGone: Boolean) {
        viewBinding.btnRefresh.visibility = if (isGone) View.GONE else View.VISIBLE
    }
}