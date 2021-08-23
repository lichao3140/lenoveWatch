package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.view.BaseActivity

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IShareService  : IProvider {
    fun showPiazzaVideoShare(activity: BaseActivity<*, *>, title:String, subTitle:String, thumb:String, shareLink:String, videoLink:String)
    fun showPiazzaImageShare(activity: BaseActivity<*, *>, title:String, subTitle:String, thumb:String, shareLink:String)
    fun showStoreShare(activity: BaseActivity<*, *>, title:String, subTitle:String, thumb:String, shareLink:String)
}