package cn.ycgo.base.medial.view.dialog

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.DialogLoadingBinding
import cn.ycgo.base.view.BaseDialog
import cn.ycgo.base.viewmodel.BaseViewModel


/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
class LoadingDialog : ILoadingDialog, BaseDialog<DialogLoadingBinding, BaseViewModel, LoadingDialog>() {
    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(true, cancelOnTouchOutside = false)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.loadingDialog)
    }

    override fun onStart() {
        super.onStart()
        val window: Window? = dialog?.window
        window?.run {
            val lp: WindowManager.LayoutParams = attributes
            lp.dimAmount = 0f
            lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            attributes = lp
        }
    }

    override fun setText(text: String) {
        viewBinding?.tvMsg?.setVisible()
        viewBinding?.tvMsg?.text = text
    }

    override fun getLayoutResId(): Int {
        return R.layout.dialog_loading
    }
}