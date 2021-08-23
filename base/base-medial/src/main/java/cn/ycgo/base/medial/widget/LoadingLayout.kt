package cn.ycgo.base.medial.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.LayoutLoadingBinding

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 加载页
 */
class LoadingLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding: LayoutLoadingBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        viewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_loading, this, true)
    }
}