package cn.ycgo.base.medial.view.dialog

import android.view.View
import androidx.annotation.ColorInt
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.setGone
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.DialogNormalBinding
import cn.ycgo.base.view.BaseDialog
import cn.ycgo.base.viewmodel.BaseViewModel

class NormalDialog : BaseDialog<DialogNormalBinding, BaseViewModel, NormalDialog>() {

    private var titleText: String? = ContextProvider.getString(R.string.text_tips)
    private var subTitleText: String? = null
    private var affirmTextString: String? = ContextProvider.getString(R.string.text_confirm)
    private var cancelTextString: String? = ContextProvider.getString(R.string.text_cancel)
    private var affirmTextColor: Int? = ContextProvider.getColor(R.color.color_theme)
    private var cancelLis: ((View) -> Boolean)? = null
    private var affirmLis: ((View) -> Boolean)? = null
    private var showTitle: Boolean = true
    private var showSubTitle: Boolean = true
    private var showAffirm: Boolean = true
    private var showCancel: Boolean = true

    override fun getLayoutResId(): Int {
        return R.layout.dialog_normal
    }

    override fun initView() {
        super.initView()
        isCancelable = false
        viewBinding?.run {
            tvTitle.text = titleText
            tvSubtitle.text = subTitleText
            tvAffirm.text = affirmTextString
            affirmTextColor?.let { tvAffirm.setTextColor(it) }
            tvCancel.text = cancelTextString

            if (showTitle) {
                tvTitle.setVisible()
            } else {
                tvTitle.setGone()
            }

            if (showSubTitle) {
                tvSubtitle.setVisible()
            } else {
                tvSubtitle.setGone()
            }

            if (showAffirm) {
                tvAffirm.setVisible()
            } else {
                tvAffirm.setGone()
            }

            if (showCancel) {
                tvCancel.setVisible()
            } else {
                tvCancel.setGone()
            }

        }
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.tvCancel?.setOnClickListener {
            val flag: Boolean = cancelLis?.invoke(it) ?: false
            if (!flag) {
                dismiss()
            }
        }
        viewBinding?.tvAffirm?.setOnClickListener {
            val flag: Boolean = affirmLis?.invoke(it) ?: false
            if (!flag) {
                dismiss()
            }
        }
    }

    fun setCancelListener(lis: (View) -> Boolean): NormalDialog {
        cancelLis = lis
        return this
    }

    fun setAffirmListener(lis: (View) -> Boolean): NormalDialog {
        affirmLis = lis
        return this
    }

    fun setCancelText(text: String): NormalDialog {
        cancelTextString = text
        viewBinding?.tvCancel?.text = cancelTextString
        return this
    }

    fun setAffirmText(text: String): NormalDialog {
        affirmTextString = text
        viewBinding?.tvAffirm?.text = affirmTextString
        return this
    }

    fun setAffirmTextColor(@ColorInt color: Int): NormalDialog {
        affirmTextColor = color
        viewBinding?.tvAffirm?.setTextColor(color)
        return this
    }

    fun setTitle(title: String): NormalDialog {
        titleText = title
        viewBinding?.tvTitle?.text = titleText
        return this
    }

    fun setSubTitle(subTitle: String): NormalDialog {
        subTitleText = subTitle
        viewBinding?.tvSubtitle?.text = subTitleText
        return this
    }

    fun titleShow(isShow: Boolean): NormalDialog {
        showTitle = isShow
        if (showSubTitle) {
            viewBinding?.tvSubtitle?.setVisible()
        } else {
            viewBinding?.tvSubtitle?.setGone()
        }
        return this
    }

    fun subTitleShow(isShow: Boolean): NormalDialog {
        showSubTitle = isShow
        if (showSubTitle) {
            viewBinding?.tvSubtitle?.setVisible()
        } else {
            viewBinding?.tvSubtitle?.setGone()
        }
        return this
    }

    fun affirmShow(isShow: Boolean): NormalDialog {
        showAffirm = isShow
        if (showAffirm) {
            viewBinding?.tvAffirm?.setVisible()
        } else {
            viewBinding?.tvAffirm?.setGone()
        }
        return this
    }

    fun cancelShow(isShow: Boolean): NormalDialog {
        showCancel = isShow
        if (showCancel) {
            viewBinding?.tvCancel?.setVisible()
        } else {
            viewBinding?.tvCancel?.setGone()
        }
        return this
    }

}


