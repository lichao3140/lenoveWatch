package cn.ycgo.base.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.common.annotation.BindEventBus
import cn.ycgo.base.common.utils.LogUtils
import cn.ycgo.base.utils.BackHandlerHelper
import cn.ycgo.base.utils.ImmersionBarFactory
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    CoroutineScope by MainScope() {

    private val toolBarBuilder: BaseToolBar.Builder? by lazy { buildToolBar() }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewBinding: VB? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected var viewModel: VM? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (isForcePortrait()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onCreate(savedInstanceState)
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
//            EventBus.getDefault().register(this)
        }

        viewBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        intent?.run {
            val valid = parseArguments(this)
            if (!valid) {
                finish()
                LogUtils.e("parseArguments invalid")
                return
            }
        }

        initViewModel() //initView依赖于ViewModel的数据，所以initViewModel需要放在前面

        if(!isInMultiWindowMode) {//多窗口下不使用
            getImmersionBarSetting().init()
        }

        initView()
        initViewListener()
        initViewObserver()

        //必须设置在setContentView之后，否则会显示异常
        //比如，在显示一个dialogFragment后（dialogFragment中也设置了沉浸式），回来时Activity的导航栏会变成默认的黑色

        viewBinding?.root?.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)
                launch {
                    delay(150)
                    onViewLoadFinish()
                }
            }
        })
    }

     open fun onViewLoadFinish() {
    }

    protected abstract fun getLayoutResId(): Int

    /**
     * 从intent中解析数据，具体子类来实现
     * @return true: 参数合法， false参数不合法
     */
    @CallSuper
    @Suppress("MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")
    protected open fun parseArguments(intent: Intent): Boolean {
        ARouter.getInstance().inject(this)
        return true
    }

    protected open fun isForcePortrait(): Boolean {
        return true
    }

    protected open fun getImmersionBarSetting(): ImmersionBar {
        return ImmersionBarFactory.default(this)
            .apply {
                //使用ImmersionBar后，ToolBar会被状态栏覆盖住，使用fitsSystemWindows可以解决这个问题
                //没有使用Toolbar&&没有使用透明状态栏，需要手动设置一下fitsSystemWindows(true)
                //fitsSystemWindows和透明状态栏同时用的话，在异性屏上状态栏会呈现黑色
                if (toolBarBuilder != null) {
                    fitsSystemWindows(true)
                }
            }
    }

    /**
     * 一般不需要复写，仅仅在泛型传递的场景下需要复写
     */
    protected open fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BaseActivity::class.java, 1)]
    }

    protected open fun buildToolBar(): BaseToolBar.Builder? {
        return null
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
     * 添加拓展的view
     */
    @CallSuper
    protected open fun initView() {
        toolBarBuilder?.build()?.attach()
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

    override fun onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    override fun onDestroy() {
        if (this.javaClass.isAnnotationPresent(BindEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
        cancel()
        super.onDestroy()
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

    //------------------------------------------------------------权限相关--------------------------------------------------------------------
    private val PERMISSION_CODE = 0x22

    /**
     * 权限回调
     */
    open fun onPermissionSuccess() {}//如果申请了权限请重写此方法,申请全向相关回调

    /**
     * 权限回调
     */
    open fun onPermissionError() {//如果申请了权限请重写此方法,申请全向相关回调

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        var haspromiss = true
        when (requestCode) {
            PERMISSION_CODE ->
                if (grantResults.isNotEmpty()) {
                    grantResults
                        .filter { it != PackageManager.PERMISSION_GRANTED }
                        .forEach { _ -> haspromiss = false }
                    if (haspromiss) {
                        onPermissionSuccess()
                    } else {
                        onPermissionError()
                    }
                }
            else ->
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * 动态申请权限
     * @param Permissions     Manifest.permission.XXX
     */
    protected fun getPermission(vararg Permissions: String) {
        var haspromiss = true
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            for (permission in Permissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(this, Permissions, PERMISSION_CODE)
                    haspromiss = false
                }
            }
            if (haspromiss) {
                onPermissionSuccess()
            }
        } else {
            onPermissionSuccess()
        }
    }
}