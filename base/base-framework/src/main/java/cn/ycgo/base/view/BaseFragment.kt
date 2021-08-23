package cn.ycgo.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.ycgo.base.common.annotation.BindEventBus
import cn.ycgo.base.common.utils.LogUtils
import cn.ycgo.base.utils.BackHandlerHelper
import cn.ycgo.base.utils.FragmentBackHandler
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.EventBus

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment(), CoroutineScope by MainScope(),
    FragmentBackHandler {
    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewBinding: VB? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewModel: VM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().register(this)
        }
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val valid = parseArguments(arguments ?: Bundle())
        if (!valid) {
            LogUtils.e("parseArguments invalid")
            return
        }

        initView()
        initViewModel()
        initViewListener()
        initViewObserver()

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
     * 初始化viewModel
     */
    @CallSuper
    protected open fun initViewModel() {
        viewModel = autoCreateViewModel()
        viewModel?.initChildViewModel()
        viewModel?.initData()
    }

    /**
     * 一般不需要复写，仅仅在泛型传递的场景下需要复写
     */
    protected open fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BaseFragment::class.java, 1)]
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

    /**
     * 初始化LiveData的观察者，即UI
     */
    @CallSuper
    protected open fun initViewObserver() {

    }

    override fun onResume() {
        super.onResume()
        viewModel?.lazyInitData()
    }

    override fun onDestroyView() {
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
        cancel()
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean {
        return BackHandlerHelper.handleBackPress(this);
    }

    /**
     * 得到旗下Fragment当前的正在使用的ViewModel，可用于数据共享。
     * <p>
     * 注意：
     * 1.观察该ViewModel中的liveData时应该使用fragment作为LifecycleOwner传入，否则会导致Fragment销毁时LiveData泄漏。
     * 2.只有Fragment attach后才能得到正确的值
     */
    protected fun <T : BaseViewModel> getFragmentVM(fragment: BaseFragment<*, *>): T? {
        return fragment.takeIf {
            it.fragmentManager != null
        }
            ?.run {
                ViewModelProvider(fragment)[getGenericsClass<T>(BaseFragment::class.java, 1)]
            }
    }
}