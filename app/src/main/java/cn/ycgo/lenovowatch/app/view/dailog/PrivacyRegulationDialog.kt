package cn.ycgo.lenovowatch.app.view.dailog

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.ycgo.app.R
import com.ycgo.app.databinding.DialogPrivacyRegulationBinding
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalValues
import cn.ycgo.base.medial.utils.AppUtil
import cn.ycgo.base.view.BaseDialog
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 21-5-7
 */
class PrivacyRegulationDialog : BaseDialog<DialogPrivacyRegulationBinding, BaseViewModel, PrivacyRegulationDialog>() {
    override fun getLayoutResId(): Int {
        return R.layout.dialog_privacy_regulation
    }

    private var cancelLis: ((View) -> Unit)? = null
    private var affirmLis: ((View) -> Unit)? = null

    override fun initView() {
        super.initView()
        isCancelable = false
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.tvAffirm?.setOnClickListener {
            AppUtil.onClickSynchronized {
                affirmLis?.invoke(it)
                dismiss()
            }
        }
        viewBinding?.tvCancel?.setOnClickListener {
            AppUtil.onClickSynchronized {
                cancelLis?.invoke(it)
                dismiss()
            }
        }

        viewBinding?.tvText?.setOnClickListener {
            AppUtil.onClickSynchronized {

            }
        }
    }

    fun setCancelListener(lis: (View) -> Unit): PrivacyRegulationDialog {
        cancelLis = lis
        return this
    }

    fun setAffirmListener(lis: (View) -> Unit): PrivacyRegulationDialog {
        affirmLis = lis
        return this
    }
}