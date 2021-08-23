package cn.ycgo.lenovowatch.components.information

import android.content.Context
import cn.ycgo.base.medial.router.IInformationService
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.lenovowatch.components.information.view.InformationFragment


/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
@Route(path = PathConstants.SERVICE_INFORMATION, name = "InformationService")
class InformationServiceImp : IInformationService {
    override fun createInformationFragment(): BaseFragment<*, *> {
        return InformationFragment.newInstance()
    }

    override fun init(context: Context?) {

    }
}