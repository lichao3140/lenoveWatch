package cn.ycgo.base.medial.router

import android.app.Activity
import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.view.BaseFragment

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IPiazzaService  : IProvider {
    fun createFollowedPiazzaFragment(): BaseFragment<*, *>
    fun createRecommendDynamicFragment(): BaseFragment<*, *>
    fun createAddressDynamicFragment(): BaseFragment<*, *>
    fun createSearchDynamicFragment(): BaseFragment<*, *>
    fun changeSearchDynamicFragmentKeyword(fragment: BaseFragment<*, *>, keyword:String)
    fun postImage(activity:Activity)
    fun postVideo(activity:Activity)
    fun openUserPiazza(userId:Int)
    fun openMyPiazza()
    fun dynamicPickLike(thread_id: Int,  result: ( Boolean) -> Unit)
    fun dynamicUnPickLike(thread_id: Int,  result: ( Boolean) -> Unit)
    fun hitShareCount(thread_id: Int)
}