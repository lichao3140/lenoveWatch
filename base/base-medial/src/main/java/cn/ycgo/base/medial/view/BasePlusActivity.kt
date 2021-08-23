package cn.ycgo.base.medial.view

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.common.utils.getCompatColor
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.view.dialog.ILoadingDialog
import cn.ycgo.base.medial.view.dialog.LoadingDialog
import cn.ycgo.base.medial.widget.ErrorLayout
import cn.ycgo.base.medial.widget.LoadingLayout
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.view.BaseActivity
import cn.ycgo.base.view.BaseDialog
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 2020/11/25
 *加强版BaseActivity
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePlusActivity<VB : ViewDataBinding, VM : BaseViewModel> : BaseActivity<VB, VM>() {

    private val loadingDialog: BaseDialog<*, *, *> by lazy { createLoadingDialog() }

    protected open fun createLoadingDialog(): BaseDialog<*, *, *> = LoadingDialog.newInstance()

    var windowColor: Int = getCompatColor(R.color.color_window_bg_color)

    override fun initView() {
        super.initView()

        viewBinding?.root?.background ?: kotlin.run {
            viewBinding?.root?.setBackgroundColor(windowColor)
        }
    }

    /**
     * 在super.initView()之前调用
     */
    fun setActivityBackgroundColor(@ColorInt color: Int) {
        windowColor = color
    }

    override fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BasePlusActivity::class.java, 1)]
    }

    protected fun showLoadingDialog() {
        loadingDialog.setCancelable(false, true)
        loadingDialog.show(this)
    }

    protected fun showLoadingDialog(
        text: String? = null,
        cancelable: Boolean = false,
        cancelOnTouchOutside: Boolean = false
    ) {
        if (!TextUtils.isEmpty(text)) {
            (loadingDialog as ILoadingDialog).setText(text ?: "")
        }
        loadingDialog.setCancelable(cancelable, cancelOnTouchOutside)
        loadingDialog.show(this)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun getLoadingDialogImp(): ILoadingDialog? {
        return (loadingDialog as ILoadingDialog)
    }

    protected open fun showLoadingLayout() {
        val contentView = findViewById<ViewGroup>(android.R.id.content)
        if (contentView.childCount == 0) {
            throw RuntimeException("cannot showLoadingLayout before setContentView")
        }

        dismissErrorLayout()

        var loadingLayout: LoadingLayout? = null

        for (childIndex in contentView.childCount - 1 downTo 0) {
            val childAt = contentView.getChildAt(childIndex)
            if (childAt is LoadingLayout) {
                loadingLayout = childAt
                break //已经存在LoadingLayout
            }
        }

        if (loadingLayout == null) {
            loadingLayout = LoadingLayout(this)
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            contentView.addView(loadingLayout, 1, params)
        }

        val childAt0 = contentView.getChildAt(0)
        childAt0.visibility = View.INVISIBLE

        loadingLayout.background = childAt0.background
    }

    protected open fun dismissLoadingLayout() {
        val contentView = findViewById<ViewGroup>(android.R.id.content)
        if (contentView.childCount == 0) {
            throw RuntimeException("cannot dismissLoadingLayout before setContentView")
        }

        val childAt0 = contentView.getChildAt(0)
        childAt0.visibility = View.VISIBLE

        for (childIndex in contentView.childCount - 1 downTo 0) {
            if (contentView.getChildAt(childIndex) is LoadingLayout) {
                contentView.removeViewAt(childIndex)
                break
            }
        }
    }

    protected open fun showErrorLayout(
        errorDrawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_global_error),
        messageString: String = getString(R.string.tip_error_layout_default),
        refreshString: String = getString(R.string.text_error_layout_refresh),
        hideRefreshBtn: Boolean = false,
        refreshAction: () -> Unit
    ) {
        val contentView = findViewById<ViewGroup>(android.R.id.content)
        if (contentView.childCount == 0) {
            throw RuntimeException("cannot showErrLayout before setContentView")
        }

        dismissLoadingLayout()

        var errorLayout: ErrorLayout? = null

        for (childIndex in contentView.childCount - 1 downTo 0) {
            val childAt = contentView.getChildAt(childIndex)
            if (childAt is ErrorLayout) {
                errorLayout = childAt
                break //已经存在ErrorLayout
            }
        }

        if (errorLayout == null) {
            errorLayout = ErrorLayout(this)
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            contentView.addView(errorLayout, 1, params)
        }

        val childAt0 = contentView.getChildAt(0)
        childAt0.visibility = View.INVISIBLE

        errorLayout.background = childAt0.background
        errorLayout.setIvError(
            errorDrawable ?: ContextCompat.getDrawable(
                this,
                R.drawable.ic_global_error
            )
        )
        errorLayout.setTvMessage(if (messageString.isNotEmpty()) messageString else getString(R.string.tip_error_layout_default))
        errorLayout.setBtnRefresh(if (refreshString.isNotEmpty()) refreshString else getString(R.string.text_error_layout_refresh)) {
            refreshAction.invoke()
            dismissErrorLayout()
        }
        errorLayout.hideRefreshBtn(hideRefreshBtn)
    }

    protected open fun dismissErrorLayout() {
        val contentView = findViewById<ViewGroup>(android.R.id.content)
        if (contentView.childCount == 0) {
            throw RuntimeException("cannot dismissErrorLayout before setContentView")
        }

        val childAt0 = contentView.getChildAt(0)
        childAt0.visibility = View.VISIBLE

        for (childIndex in contentView.childCount - 1 downTo 0) {
            if (contentView.getChildAt(childIndex) is ErrorLayout) {
                contentView.removeViewAt(childIndex)
                break
            }
        }
    }

    /**
     * 使内容不填充到状态栏
     */
    fun marginStatusBar() {
        if(!isInMultiWindowMode) {//多窗口下不使用
            viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
        }
    }

    /**
     * 得到旗下Fragment当前的正在使用的ViewModel，可用于数据共享。
     * <p>
     * 注意：
     * 1.观察该ViewModel中的liveData时应该使用fragment作为LifecycleOwner传入，否则会导致Fragment销毁时LiveData泄漏。
     * 2.只有Fragment attach后才能得到正确的值
     */
    protected fun <T : BaseViewModel> getFragmentVM(fragment: BasePlusFragment<*, *>): T? {
        return fragment.takeIf {
            it.fragmentManager != null
        }
            ?.run {
                ViewModelProvider(fragment)[getGenericsClass<T>(BasePlusFragment::class.java, 1)]
            }
    }
}