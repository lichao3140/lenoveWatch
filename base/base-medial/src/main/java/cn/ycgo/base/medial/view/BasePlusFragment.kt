package cn.ycgo.base.medial.view

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.view.dialog.ILoadingDialog
import cn.ycgo.base.medial.view.dialog.LoadingDialog
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.view.BaseActivity
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
abstract class BasePlusFragment<VB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<VB, VM>() {

    private var loadingDialog: LoadingDialog = LoadingDialog.newInstance()

    private var firstVisible = true

    override fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BasePlusFragment::class.java, 1)]
    }

    fun getLoadingDialogImp(): ILoadingDialog? {
        return (loadingDialog)
    }

    protected fun showLoadingDialog() {
        loadingDialog.show(this)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun onResume() {
        super.onResume()
        if (firstVisible) {
            onFirstVisible()
            firstVisible = false
        }
    }

    /**
     * 首次可见
     */
    protected open fun onFirstVisible() {}

    /**
     * 得到所属Activity当前的正在使用的ViewModel，可用于数据共享
     *
     * <p>
     * 注意：
     * 1.只有Fragment attach后（即[getActivity]不为空）才能得到正确的值
     */
    protected fun <T : BaseViewModel> getActivityVM(): T? {
        activity?.run {
            this as? BasePlusActivity<*, *>
        }
            ?.run {
                return ViewModelProvider(this)[getGenericsClass<T>(BasePlusActivity::class.java, 1)]
            }

        activity?.run {
            this as? BaseActivity<*, *>
        }
            ?.run {
                return ViewModelProvider(this)[getGenericsClass<T>(BaseActivity::class.java, 1)]
            }
        return null
    }

    /**
     * 使内容不填充到状态栏
     */
    fun marginStatusBar(){
        viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this),0,0)
    }
}