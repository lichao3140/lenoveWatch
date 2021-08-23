package cn.ycgo.lenovowacth.components.browser.view

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalValues
import cn.ycgo.base.medial.utils.ToastUtil
import com.ycgo.components.browser.R
import com.ycgo.components.browser.databinding.ActivityWebBinding

/**
 * @author Brian
 * @date :2020/9/19 16:20
 * @description:
 * 接收  GlobalValues.TITLE    String 标题
 * 接收  GlobalValues.WEB_LINK    String 链接 必须
 */
@Route(path = PathConstants.ACTIVITY_WEB)
open class WebActivity : AppCompatActivity() {
    private lateinit var webFragment: WebFragment
    private lateinit var viewBinding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val url = intent?.getStringExtra(GlobalValues.WEB_LINK)
        val title = intent?.getStringExtra(GlobalValues.TITLE)
        if (TextUtils.isEmpty(url)) {
            ToastUtil.show(R.string.tip_request_link_state_fail)
            finish()
        }

        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        window.statusBarColor = Color.WHITE
        ContextProvider.setStatusBarTextColor(window,false)

        viewBinding.tvTitle.text=title

        val transaction = supportFragmentManager.beginTransaction()
        webFragment = WebFragment.newInstance(url!!)
        transaction.add(viewBinding.vWebLayout.id, webFragment)
        transaction.commit()
        initViewListener()
    }

    private fun initViewListener() {

        viewBinding.ivBack.setOnClickListener {
            doBack()
        }

        webFragment.setTitleListener {
            if (!TextUtils.isEmpty(it)) {
                    viewBinding.tvTitle.text=it
            }
        }
    }

    protected fun doBack() {
        if (!webFragment.doBack()) {
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doBack()
            return true
        }
        return false
    }
}