package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.medial.storage.bean.IBannerBean

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IAdService  : IProvider {
    fun getFindDoctorBanner(list: (List<IBannerBean>) -> Unit)
}