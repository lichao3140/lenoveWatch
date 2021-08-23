package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.medial.storage.bean.SpreadQrcode
import cn.ycgo.base.medial.storage.bean.UserDetailBean
import cn.ycgo.base.view.BaseFragment

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IUserService : IProvider {
    fun getMineUserInfo(user: (UserDetailBean) -> Unit)
    fun getUserInfo(userId: Int, user: (UserDetailBean) -> Unit)
    fun doFollowUser(userId: Int, result: (Boolean) -> Unit)
    fun doUnFollowUser(userId: Int, result: (Boolean) -> Unit)
    fun getUserPhone(phone: (String) -> Unit)
    fun refreshToken(lis: (Boolean) -> Unit)
    fun createSearchUserFragment(): BaseFragment<*, *>
    fun changeSearchUserFragmentKeyword(fragment: BaseFragment<*, *>, keyword:String)
    fun getSpreadQrcode(user: (SpreadQrcode) -> Unit)
}