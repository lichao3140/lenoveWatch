package cn.ycgo.lenovowatch.components.mine

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.lenovowatch.components.mine.view.MineFragment
import cn.ycgo.base.medial.router.IMineService


/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
@Route(path = PathConstants.SERVICE_MINE, name = "MineService")
class MineServiceImp : IMineService {
    override fun createMineFragment(): BaseFragment<*, *> {
        return MineFragment.newInstance()
    }

    override fun init(context: Context?) {

    }
}