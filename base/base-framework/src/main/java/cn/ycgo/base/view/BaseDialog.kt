package cn.ycgo.base.view

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import cn.ycgo.base.common.utils.LogUtils
import cn.ycgo.base.utils.BackHandlerHelper
import cn.ycgo.base.utils.FragmentBackHandler
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 注：如果需要使宽度充满，设置固定值=360dp即可。高宽不支持MATCH，支持固定值、WRAP
 */
abstract class BaseDialog<VB : ViewDataBinding, VM : BaseViewModel, out T : BaseDialog<VB, VM, T>> : AppCompatDialogFragment(),
    FragmentBackHandler {
    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewBinding: VB? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewModel: VM? = null

    var isCanceledOnTouchOutside: Boolean = true
        private set
    private var isShown = false

    private var dismissListener: (() -> Unit)? = null

    /**
     * 必须返回父类的dialog，否则的话显示样式会有问题
     */
    @CallSuper
    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).defaultDialogSetting()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //attachToParent=false使添加父布局可以使LayoutParams生效，但是不会真的作为父布局，详细见该方法源码解释
        //需要注意的是，这里和BaseDialog不一样，因为window的宽度使用了MATCH，所以这里宽度MATCH_PARENT是有效的
        //但是高度MATCH_PARENT依旧是会被解析成WRAP
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container ?: FrameLayout(inflater.context), false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = autoCreateViewModel()

        arguments?.run {
            val valid = parseArguments(this)
            if (!valid) {
                LogUtils.e("parseArguments invalid")
                return
            }
        }

        initViewModel()

        initView()
        initViewListener()
        initViewObserver()
    }

    protected open fun initViewModel() {
        viewModel?.initChildViewModel()
        viewModel?.initData()
    }

    protected abstract fun getLayoutResId(): Int

    /**
     * 从bundle中解析数据，具体子类来实现
     * @return true: 参数合法， false参数不合法
     */
    @Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
    protected open fun parseArguments(bundle: Bundle): Boolean {
        return true
    }

    /**
     * 一般不需要复写，仅仅在泛型传递的场景下需要复写
     */
    protected open fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BaseDialog::class.java, 1)]
    }

    /**
     * 添加拓展的view
     */
    @CallSuper
    protected open fun initView() {

    }

    /**
     * 初始化View的监听事件
     */
    @CallSuper
    protected open fun initViewListener() {
    }

    @Suppress("UNCHECKED_CAST")
    fun setCancelable(cancelable: Boolean = true, cancelOnTouchOutside: Boolean = true): T {
        dialog?.setCanceledOnTouchOutside(cancelOnTouchOutside)
        isCanceledOnTouchOutside = cancelOnTouchOutside
        super.setCancelable(cancelable) //总开关，应放在最后，否则会被setCanceledOnTouchOutside修正
        return this as T
    }

    /**
     * 初始化LiveData的观察者，即UI
     */
    @CallSuper
    protected open fun initViewObserver() {

    }

    /**
     * 1.dialogFragment 在已经显示的情况下不能多次显示；
     * 2.在调用dismiss/dismissAllowingStateLoss的稍后可以再次showNow，注意是稍后；
     * 3.在调用dismiss/dismissAllowingStateLoss的情况下可以立即再次show，注意是立即并且是show，不是showNow；
     * 4.在普遍场景下，dialog不需要多次显示，也没有这个必要，dialog应该是一次性被消费的
     * 5.使用showNow和dismissAllowingStateLoss的即时性来避免如回退等边界场景导致fragment的异常
     */
    private fun show(manager: FragmentManager) {
        if (isShown) { //拒绝重复添加fragment
            return
        }
        isShown = true
        //https://www.jianshu.com/p/a60709738671
        //重写DialogFragment的show方法
        //可以查看showAllowingStateLoss不过被hide
        //通过反射拿到这两个变量进行设置
        kotlin.runCatching {
            val mDismissed = DialogFragment::class.java.getDeclaredField("mDismissed")
            mDismissed.isAccessible = true
            mDismissed.set(this, false)
            val mShownByMe = DialogFragment::class.java.getDeclaredField("mShownByMe")
            mShownByMe.isAccessible = true
            mShownByMe.set(this, true)
            manager.beginTransaction()
                .add(this, tag)
                .commitAllowingStateLoss()
        }
    }

    @CallSuper
    open fun show(fragment: Fragment) {
        show(fragment.childFragmentManager)
    }

    @CallSuper
    open fun show(activity: AppCompatActivity) {
        show(activity.supportFragmentManager)
    }

    override fun dismiss() {
        if (!isShown) {
            return
        }
        isShown = false
        super.dismissAllowingStateLoss()
    }

    fun isShown(): Boolean {
        return isShown
    }

    /**
     * 设置对话框消失的监听
     */
    @Suppress("UNCHECKED_CAST")
    fun setDismissListener(block: () -> Unit): T {
        dismissListener = block
        return this as T
    }

    override fun onBackPressed(): Boolean {
        return BackHandlerHelper.handleBackPress(this)
    }

    /**
     * 得到所属Activity当前的正在使用的ViewModel，可用于数据共享
     *
     * <p>
     * 注意：
     * 1.只有Fragment attach后（即[getActivity]不为空）才能得到正确的值
     */
    protected fun <T : BaseViewModel> getActivityVM(): T? {
        activity?.run {
            this as? BaseActivity<*, *>
        }
            ?.run {
                return ViewModelProvider(this)[getGenericsClass<T>(BaseActivity::class.java, 1)]
            }

        activity?.run {
            this as? BaseActivity<*, *>
        }
            ?.run {
                return ViewModelProvider(this)[getGenericsClass<T>(BaseActivity::class.java, 1)]
            }
        return null
    }

    @Deprecated("replace with show(manager: FragmentManager)", ReplaceWith("show(manager)"), DeprecationLevel.HIDDEN)
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    @Deprecated("replace with show(manager: FragmentManager)", ReplaceWith("show(manager: FragmentManager)"), DeprecationLevel.HIDDEN)
    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

    @Deprecated("replace with show(manager: FragmentManager)", ReplaceWith("show(manager)"), DeprecationLevel.HIDDEN)
    override fun showNow(manager: FragmentManager, tag: String?) {
        super.showNow(manager, tag)
    }

    @Deprecated("replace with dismiss()", ReplaceWith("dismiss("), DeprecationLevel.HIDDEN)
    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
    }

    @Deprecated("setShowsDialog is deprecated", ReplaceWith(""), DeprecationLevel.HIDDEN)
    override fun getShowsDialog(): Boolean {
        return super.getShowsDialog()
    }

    @Deprecated("cannot set ShowsDialog false", ReplaceWith(""), DeprecationLevel.HIDDEN)
    override fun setShowsDialog(showsDialog: Boolean) {
        super.setShowsDialog(showsDialog)
    }

    private fun Dialog.defaultDialogSetting(): Dialog {
        setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //去掉对话框的背景，否则的话会叠加在布局的底层(蒙版之上，布局底层之下)
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShown = false
        dismissListener?.invoke()
    }
}
