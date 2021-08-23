package cn.ycgo.lenovowacth.components.browser

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.medial.router.IBrowserService
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.lenovowacth.components.browser.view.WebFragment

/**
 *Author:Kgstt
 *Time: 21-2-22
 */
@Route(path = PathConstants.SERVICE_BROWSER, name = "BrowserService")
class BrowserServiceImp : IBrowserService {
    override fun createWebFragment(link: String): BaseFragment<*, *> {
        return WebFragment.newInstance(link)
    }

    override fun init(context: Context?) {

    }
}