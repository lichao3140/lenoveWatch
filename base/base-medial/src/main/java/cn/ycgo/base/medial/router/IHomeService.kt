package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.view.BaseFragment

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IHomeService  : IProvider {
    fun createHomeFragment(): BaseFragment<*, *>
}