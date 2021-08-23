package cn.ycgo.base.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@Suppress("unused")
abstract class BaseToolBar<VB : ViewDataBinding, T : BaseToolBar.Builder>(protected val builder: T) {
    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewBinding: VB? = null

    private val contentView: ViewGroup = builder.activity.findViewById(android.R.id.content) as ViewGroup

    init {
        //获取Activity的根视图，这个是AppCompatActivity才有的
        onCreate()
    }

    private fun onCreate() {
        val inflate = LayoutInflater.from(builder.activity)
            .inflate(getLayoutResId(), contentView, false)
        viewBinding = DataBindingUtil.bind(inflate)
        initView()
    }

    /**
     * 自己封装的Toolbar布局
     */
    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    /**
     * 设置Toolbar布局中的文本，图片，点击事件
     */
    protected open fun initView() {

    }

    fun attach() {
        if (contentView.childCount == 0) {
            throw RuntimeException("cannot attach ToolBar before setContentView")
        }
        val rootView = viewBinding?.root ?: return

        contentView.addView(rootView)

        rootView.post {
            val layoutParams = contentView.getChildAt(0).layoutParams as FrameLayout.LayoutParams
            layoutParams.topMargin += rootView.height
            contentView.getChildAt(0).layoutParams = layoutParams
        }
    }

    fun getRootView(): View? {
        return viewBinding?.root
    }

    abstract class Builder(val activity: AppCompatActivity) {
        abstract fun build(): BaseToolBar<*, *>
    }
}
