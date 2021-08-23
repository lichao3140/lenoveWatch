package cn.ycgo.lenovowatch.app.view


import android.Manifest
import com.alibaba.android.arouter.launcher.ARouter
import com.lxc.amap.LxcAmap
import com.ycgo.app.R
import com.ycgo.app.databinding.ActivitySplashBinding
import cn.ycgo.lenovowatch.app.view.dailog.PrivacyRegulationDialog
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalSpEngine
import cn.ycgo.base.medial.view.BasePlusActivity
import cn.ycgo.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Author:Kgstt
 *Time: 21-1-27
 */
class SplashActivity : BasePlusActivity<ActivitySplashBinding, BaseViewModel>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        super.initView()
        getPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
        )
    }

    override fun onPermissionSuccess() {
        super.onPermissionSuccess()
        LxcAmap.init()
        LxcAmap.getLocation { }
        onNext()
    }

    override fun onPermissionError() {
        super.onPermissionError()
        onNext()
    }

    var privacyRegulationsDialog: PrivacyRegulationDialog? = null
    private fun onNext() {
        if (GlobalSpEngine.isShowPrivacyRegulations()) {
            if (privacyRegulationsDialog == null) {
                privacyRegulationsDialog = PrivacyRegulationDialog()
                    .setAffirmListener {
                        GlobalSpEngine.setShowPrivacyRegulations(false)
                        toHome()
                    }
                    .setCancelListener {
                        finish()
                    }
            }
            privacyRegulationsDialog?.show(this)
        } else {
            toHome()
        }
    }

    private fun toHome(){
        launch {
            delay(1000)
            ARouter.getInstance().build(PathConstants.ACTIVITY_MAIN).navigation()
            finish()
        }
    }
}