package cn.ycgo.base.medial.view

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ClickUtils
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 * ```
 * author: Kgstt
 * time  : 5/24/21 5:27 PM
 * ```
 */
abstract class BaseBoostActivity<VB : ViewDataBinding, VM : BaseViewModel>: BasePlusActivity<VB, VM>() {
    private val mClickListener = View.OnClickListener { v -> onDebouncingClick(v) }

    override fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BaseBoostActivity::class.java, 1)]
    }

    open fun onDebouncingClick(view: View){}

    open fun applyDebouncingClickListener(vararg views: View?) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener)
        ClickUtils.applyPressedViewScale(*views)
    }

}