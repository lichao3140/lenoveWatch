package cn.ycgo.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import cn.ycgo.base.common.annotation.BindEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.EventBus

/**
 *Author:Kgstt
 *Time: 2020/11/23
 */
open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {
    // Can be reused
    private val viewModelProvider by lazy { ViewModelProvider(ViewModelStoreOwner { childViewModelStore }) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected val lifecycleOwner: LifecycleOwner = LifecycleOwner { lifecycle }
    private val lifecycle: LifecycleRegistry = LifecycleRegistry(lifecycleOwner)

    private val childViewModelStore = ViewModelStore()
    private val childViewModelSet =
        mutableSetOf<BaseViewModel>() //因为ViewModelStore中的map是不可见的，因此自己维护了一个ViewModel的集合

    private var hasInitData = false //是否已经执行过了initData()
    private var hasLazyInitData = false //是否已经执行过了lazyInitData()

    init {
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().register(this)
        }
        //因为ViewModel的lifecycle只做数据处理使用，所以只需要关注观察者的注册和销毁即可。
        //由LiveData.shouldBeActive可知，LiveData的观察者至少需要处于STARTED，才算活跃状态。
        //而通过LifecycleRegistry可知，当发送ON_START事件时，LiveData=STARTED
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    /**
     * 初始化childViewModel
     */
    @CallSuper
    open fun initChildViewModel() {
    }

    /**
     * 初始化数据
     */
    @CallSuper
    open fun initData() {
        if (hasInitData) {
            return
        }
        hasInitData = true

        for (childVM in childViewModelSet) {
            childVM.initData()
        }
    }

    /**
     * 延时初始化数据（第一次对用户可见时）
     */
    @CallSuper
    open fun lazyInitData() {
        if (hasLazyInitData) {
            return
        }
        hasLazyInitData = true

        for (childVM in childViewModelSet) {
            childVM.lazyInitData()
        }
    }

    @Suppress("unused")
    protected fun <T : BaseViewModel> createChildModel(modelClass: Class<T>): T {
        val vm = viewModelProvider[modelClass]
        childViewModelSet.add(vm)
        if (hasInitData) {
            vm.initData()
        }
        if (hasLazyInitData) {
            vm.lazyInitData()
        }
        return vm
    }

    override fun onCleared() {
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
        childViewModelSet.clear()
        childViewModelStore.clear() //ViewModelStore.clear中会自动执行每个ViewModel的onCleared
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        cancel()
        super.onCleared()
    }

}