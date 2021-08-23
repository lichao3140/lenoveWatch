package cn.ycgo.lenovowatch.compenents.relatives

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.view.BaseFragment
import cn.ycgo.base.medial.router.IMineService
import cn.ycgo.base.medial.router.IRelativesService
import cn.ycgo.lenovowatch.compenents.relatives.view.RelativesFragment


/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
@Route(path = PathConstants.SERVICE_RELATIVES, name = "RelativesService")
class RelativesServiceImp : IRelativesService {

    override fun createRelativesFragment(): BaseFragment<*, *> {
        return RelativesFragment.newInstance()
    }

    override fun init(context: Context?) {

    }
}