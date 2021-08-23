package cn.ycgo.base.medial.widget.toolbar


import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.common.utils.setInVisible
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.LayoutNormalToolbarBinding
import cn.ycgo.base.view.BaseToolBar


/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@Suppress("MemberVisibilityCanBePrivate")
class NormalToolBar internal constructor(builder: Builder) : BaseToolBar<LayoutNormalToolbarBinding, NormalToolBar.Builder>(builder) {
    enum class Gravity {
        CENTER, LEFT
    }

    override fun getLayoutResId(): Int {
        return R.layout.layout_normal_toolbar
    }

    override fun initView() {
        super.initView()
        viewBinding?.clRoot?.apply {
            if (builder.gravity == Gravity.CENTER) {
                return@apply
            }

            if (builder.gravity == Gravity.LEFT) {
                val constraintSet = ConstraintSet()
                constraintSet.clone(this)
                constraintSet.connect(R.id.tv_title, ConstraintSet.START, R.id.iv_back, ConstraintSet.END, 8.dpToPxOffset())
                constraintSet.connect(R.id.tv_title, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                constraintSet.applyTo(this)
                viewBinding?.tvTitle?.gravity = android.view.Gravity.START or android.view.Gravity.CENTER_VERTICAL
            }
        }

        viewBinding?.tvTitle?.text = builder.title

        viewBinding?.tvAction?.apply {
            text = builder.action
            if (builder.action.isNotEmpty()) {
                visibility = View.VISIBLE
            }
            setOnClickListener(builder.actionListener)
        }

        viewBinding?.ivAction?.apply {
            setImageDrawable(builder.actionDrawable)
            if (builder.actionDrawable != null) {
                visibility = View.VISIBLE
            }
            setOnClickListener(builder.actionListener)
        }

        viewBinding?.ivBack?.setOnClickListener {
            if (builder.backEvent?.invoke(it) == true) {
                return@setOnClickListener
            }
            builder.activity.finish()
        }

        viewBinding?.tbToolbarLayout?.setBackgroundColor(builder.bgColor)

    }

    fun setTvTitleColor(@ColorInt color: Int) {
        viewBinding?.tvTitle?.setTextColor(color)
    }

    fun setTvTitle(title: String) {
        builder.title = title

        viewBinding?.tvTitle?.text = title
    }

    fun setIvBack(drawable: Drawable?) {
        viewBinding?.ivBack?.setImageDrawable(drawable)
    }

    fun setIvAction(drawable: Drawable?) {
        builder.actionDrawable = drawable

        if (drawable != null) {
            viewBinding?.tvAction?.setInVisible()
            viewBinding?.ivAction?.setVisible()
            viewBinding?.ivAction?.setImageDrawable(drawable)
        } else {
            viewBinding?.ivAction?.setInVisible()
        }
    }

    fun setTvAction(action: String) {
        builder.action = action

        if (action.isNotEmpty()) {
            viewBinding?.ivAction?.setInVisible()
            viewBinding?.tvAction?.setVisible()
            viewBinding?.tvAction?.text = action
        } else {
            viewBinding?.tvAction?.setInVisible()
        }
    }

    fun setBackground(drawable: Drawable?) {
        viewBinding?.root?.background = drawable
    }

    class Builder(activity: AppCompatActivity) : BaseToolBar.Builder(activity) {
        internal var title = ""
        internal var gravity = Gravity.CENTER
        internal var action = ""
        internal var bgColor:Int = ContextProvider.getColor(R.color.color_system_status_bar)
        internal var actionDrawable: Drawable? = null
        internal var actionListener: View.OnClickListener? = null
        internal var backEvent: ((View) -> Boolean)? = null

        fun setTitle(@StringRes titleId: Int): Builder {
            return setTitle(ContextProvider.getString(titleId))
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setTitleGravity(gravity: Gravity): Builder {
            this.gravity = gravity
            return this
        }

        fun setAction(@StringRes actionId: Int): Builder {
            return setAction(ContextProvider.getString(actionId))
        }

        fun setAction(action: String): Builder {
            this.action = action
            this.actionDrawable = null
            return this
        }

        fun setActionDrawable(@DrawableRes actionDrawableId: Int): Builder {
            return ContextProvider.getDrawable(actionDrawableId)?.run {
                setActionDrawable(this)
            }
                ?: this
        }

        fun setActionDrawable(actionDrawable: Drawable): Builder {
            this.actionDrawable = actionDrawable
            this.action = ""
            return this
        }

        fun setBgColor(@ColorInt color: Int): Builder {
            bgColor=color
            return this
        }

        fun setActionListener(actionListener: View.OnClickListener): Builder {
            this.actionListener = actionListener
            return this
        }

        fun setBackEvent(event: (View) -> Boolean): Builder {
            this.backEvent = event
            return this
        }

        override fun build(): NormalToolBar {
            return NormalToolBar(this)
        }
    }
}