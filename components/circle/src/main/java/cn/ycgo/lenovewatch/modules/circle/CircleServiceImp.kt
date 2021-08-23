package cn.ycgo.lenovewatch.modules.circle

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.medial.router.ICircleService
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.lenovewatch.modules.circle.view.CircleFragment


/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
@Route(path = PathConstants.SERVICE_CIRCLE, name = "CircleService")
class CircleServiceImp : ICircleService {
    override fun createCircleFragment(): BaseFragment<*, *> {
        return CircleFragment.newInstance()
    }

    override fun init(context: Context?) {

    }
}