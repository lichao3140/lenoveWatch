package cn.ycgo.base.view
import android.content.res.Configuration
import android.view.Gravity
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ycgo.base.R
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 注：宽支持WRAP、MATCH、固定值，高只支持WRAP和固定值
 */
abstract class BaseBottomDialog<VB : ViewDataBinding, VM : BaseViewModel>(private val includeNavigationBar: Boolean = false) : BaseDialog<VB, VM, BaseBottomDialog<VB, VM>>() {

    override fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BaseBottomDialog::class.java, 1)]
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setWindowAnimations(R.style.BottomDialog_DefaultAnimation)
        }
    }

    override fun onStop() {
        super.onStop()
        dialog?.window?.setWindowAnimations(R.style.BottomDialog_DefaultExitAnim)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}