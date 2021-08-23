package cn.ycgo.base.medial.widget.toolbar

import android.text.TextUtils
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.setGone
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.LayoutInteractivelToolbarBinding
import cn.ycgo.base.view.BaseToolBar

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@Suppress("MemberVisibilityCanBePrivate")
class InteractiveToolBar internal constructor(builder: Builder) :
    BaseToolBar<LayoutInteractivelToolbarBinding, InteractiveToolBar.Builder>(builder) {

    enum class WidgetSet {
        CANTER_TEXT, LEFT_TEXT, RIGHT_TEXT, LEFT_IMAGE, RIGHT_IMAGE
    }

    class ViewItem(val set: WidgetSet, val value: Any)

    override fun getLayoutResId(): Int {
        return R.layout.layout_interactivel_toolbar
    }

    override fun initView() {
        super.initView()

        viewBinding?.textCenter?.setOnClickListener {
            builder.listener?.invoke(WidgetSet.CANTER_TEXT)
        }
        viewBinding?.textLeft?.setOnClickListener {
            builder.listener?.invoke(WidgetSet.LEFT_TEXT)
        }
        viewBinding?.textRight?.setOnClickListener {
            builder.listener?.invoke(WidgetSet.RIGHT_TEXT)
        }
        viewBinding?.imageLeft?.setOnClickListener {
            builder.listener?.invoke(WidgetSet.LEFT_IMAGE)
        }
        viewBinding?.imageRight?.setOnClickListener {
            builder.listener?.invoke(WidgetSet.RIGHT_IMAGE)
        }

        if (!TextUtils.isEmpty(builder.centerText)) {
            viewBinding?.textCenter.apply {
                this?.visibility = View.VISIBLE
                this?.text = builder.centerText
                this?.setTextColor(builder.centerTextColor)
            }
        } else {
            viewBinding?.textCenter?.setGone()
        }

        if (!TextUtils.isEmpty(builder.leftText)) {
            viewBinding?.textLeft.apply {
                this?.setVisible()
                this?.text = builder.leftText
                this?.setTextColor(builder.leftTextColor)
            }
        } else {
            viewBinding?.textLeft?.setGone()
        }

        if (!TextUtils.isEmpty(builder.rightText)) {
            viewBinding?.textRight.apply {
                this?.setVisible()
                this?.text = builder.rightText
                this?.setTextColor(builder.rightTextColor)
            }
        } else {
            viewBinding?.textRight?.setGone()
        }

        if (builder.leftImageRes != -1) {
            viewBinding?.imageLeft.apply {
                this?.setVisible()
                this?.setImageResource(builder.leftImageRes)
            }
        } else {
            viewBinding?.imageLeft?.setGone()
        }

        if (builder.rightImageRes != -1) {
            viewBinding?.imageRight.apply {
                this?.setVisible()
                this?.setImageResource(builder.rightImageRes)
            }
        } else {
            viewBinding?.imageRight?.setGone()
        }

        viewBinding?.tbToolbarLayout?.setBackgroundColor(builder.bgColor)

        builder.textActionBuilder?.lis = object : ActionListener {
            override fun action(item: ViewItem) {
                when (item.set) {
                    WidgetSet.CANTER_TEXT -> {
                        if (viewBinding?.textCenter?.visibility == View.VISIBLE) {
                            viewBinding?.textCenter?.text = item.value.toString()
                        }
                    }
                    WidgetSet.LEFT_TEXT -> {
                        if (viewBinding?.textLeft?.visibility == View.VISIBLE) {
                            viewBinding?.textLeft?.text = item.value.toString()
                        }
                    }
                    WidgetSet.RIGHT_TEXT -> {
                        if (viewBinding?.textRight?.visibility == View.VISIBLE) {
                            viewBinding?.textRight?.text = item.value.toString()
                        }
                    }
                }
            }
        }
        builder.textActionBuilder?.create()

        builder.imageActionBuilder?.lis = object : ActionListener {
            override fun action(item: ViewItem) {
                when (item.set) {
                    WidgetSet.LEFT_IMAGE -> {
                        if (viewBinding?.imageLeft?.visibility == View.VISIBLE) {
                            viewBinding?.imageLeft?.setImageResource(item.value.toString().toInt())
                        }
                    }
                    WidgetSet.RIGHT_IMAGE -> {
                        if (viewBinding?.imageRight?.visibility == View.VISIBLE) {
                            viewBinding?.imageRight?.setImageResource(item.value.toString().toInt())
                        }
                    }
                }
            }
        }
        builder.imageActionBuilder?.create()

        builder.visibleActionBuilder?.lis = object : ActionListener {
            override fun action(item: ViewItem) {
                when (item.set) {
                    WidgetSet.CANTER_TEXT -> {
                        viewBinding?.textCenter?.visibility = item.value.toString().toInt()
                    }
                    WidgetSet.LEFT_TEXT -> {
                        viewBinding?.textLeft?.visibility = item.value.toString().toInt()
                    }
                    WidgetSet.RIGHT_TEXT -> {
                        viewBinding?.textRight?.visibility = item.value.toString().toInt()
                    }
                    WidgetSet.LEFT_IMAGE -> {
                        viewBinding?.imageLeft?.visibility = item.value.toString().toInt()
                    }
                    WidgetSet.RIGHT_IMAGE -> {
                        viewBinding?.imageRight?.visibility = item.value.toString().toInt()
                    }
                }
            }
        }
        builder.visibleActionBuilder?.create()
    }

    class Builder(activity: AppCompatActivity) : BaseToolBar.Builder(activity) {

        var listener: ((widgetSet: WidgetSet) -> Unit)? = null
        var centerText: String? = null
        var leftText: String? = null
        var rightText: String? = null
        var leftImageRes: Int = -1
        var rightImageRes: Int = -1
        var rightTextColor: Int = ContextProvider.getColor(R.color.font_black)
        var leftTextColor: Int = ContextProvider.getColor(R.color.font_black)
        var centerTextColor: Int = ContextProvider.getColor(R.color.font_black)
        internal var bgColor: Int = ContextProvider.getColor(R.color.color_system_status_bar)

        internal var textActionBuilder: ResAction? = null
        internal var imageActionBuilder: ResAction? = null
        internal var visibleActionBuilder: ResAction? = null

        fun textAction(action: ResAction?): Builder {
            textActionBuilder = action
            return this
        }

        fun imageAction(action: ResAction?): Builder {
            imageActionBuilder = action
            return this
        }

        fun visibleAction(action: ResAction?): Builder {
            visibleActionBuilder = action
            return this
        }

        fun setClickListener(listener: (widgetSet: WidgetSet) -> Unit): Builder {
            this.listener = listener
            return this
        }

        fun setBgColor(@ColorInt color: Int): Builder {
            bgColor = color
            return this
        }

        fun setCenterText(centerText: String): Builder {
            this.centerText = centerText
            return this
        }

        fun setLeftText(leftText: String): Builder {
            this.leftText = leftText
            return this
        }

        fun setRightText(rightText: String): Builder {
            this.rightText = rightText
            return this
        }

        fun setLeftImage(@DrawableRes leftImageRes: Int): Builder {
            this.leftImageRes = leftImageRes
            return this
        }

        fun setRightImageRes(@DrawableRes rightImageRes: Int): Builder {
            this.rightImageRes = rightImageRes
            return this
        }

        fun setViewItem(viewItem: ViewItem): Builder {
            when (viewItem.set) {
                WidgetSet.CANTER_TEXT -> setCenterText(viewItem.value.toString())
                WidgetSet.LEFT_TEXT -> setLeftText(viewItem.value.toString())
                WidgetSet.RIGHT_TEXT -> setRightText(viewItem.value.toString())
                WidgetSet.LEFT_IMAGE -> setLeftImage(viewItem.value.toString().toInt())
                WidgetSet.RIGHT_IMAGE -> setRightImageRes(viewItem.value.toString().toInt())
            }
            return this
        }

        override fun build(): BaseToolBar<*, *> {
            return InteractiveToolBar(this)
        }
    }

    abstract class ResAction {
        var lis: ActionListener? = null
        abstract fun create()
    }

    interface ActionListener {
        fun action(item: ViewItem)
    }
}

