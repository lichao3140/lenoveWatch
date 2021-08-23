package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.view.BaseFragment

/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
interface ILocationService:IProvider {
    fun createLocationFragment(): BaseFragment<*, *>
}