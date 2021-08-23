package cn.ycgo.base.medial.router

import cn.ycgo.base.view.BaseFragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IInformationService  : IProvider {

    fun createInformationFragment(): BaseFragment<*, *>
}