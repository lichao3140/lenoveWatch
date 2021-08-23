package cn.ycgo.lenovowacth.components.browser.view

import android.graphics.Color
import android.text.TextUtils

import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalValues
import cn.ycgo.base.medial.utils.ToastUtil
import cn.ycgo.base.medial.view.BasePlusActivity
import cn.ycgo.base.viewmodel.BaseViewModel
import com.ycgo.components.browser.R
import com.ycgo.components.browser.databinding.ActivityWebGameBinding

/**
 * @author Brian
 * @date :2020/9/19 16:20
 * @description:
 * 接收  GlobalValues.WEB_LINK    String 链接 必须
 */
@Route(path = PathConstants.ACTIVITY_WEB_GAME)
class WebGameActivity : BasePlusActivity<ActivityWebGameBinding, BaseViewModel>() {
    private lateinit var webFragment : WebFragment
    override fun getImmersionBarSetting(): ImmersionBar {
        return super.getImmersionBarSetting()
            .transparentStatusBar()
            .navigationBarColor(R.color.color_transparency)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .fullScreen(true)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_web_game
    }

    override fun initView() {
        setActivityBackgroundColor(Color.BLACK)
        super.initView()
        val url = intent?.getStringExtra(GlobalValues.WEB_LINK)
        if (TextUtils.isEmpty(url)) {
            ToastUtil.show(R.string.tip_request_link_state_fail)
            finish()
        }
        val transaction = supportFragmentManager.beginTransaction()
        webFragment = WebFragment.newInstance(url!!)
        viewBinding?.vWebLayout?.id?.let { transaction.add(it, webFragment) }
        transaction.commit()
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.vClose?.setOnClickListener { finish() }
    }
}