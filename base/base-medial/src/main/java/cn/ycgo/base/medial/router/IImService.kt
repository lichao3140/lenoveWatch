package cn.ycgo.base.medial.router

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
interface IImService : IProvider {
    fun startChat(activity: Activity, imUserName: String)
    fun startAskChat(activity: Activity, imUserName: String, question_id: Int)
    fun saveChatUser(username: String, avatar: String, imUserName: String)
    fun getChatUnReadCount(userName: String): Int
    fun getAllChatUnReadCount(): Int
    fun getAllPiazzaChatUnReadCount(): Int
    fun getAllConsultChatUnReadCount(): Int
    fun refreshAllPiazzaChatUnReadCount(call: (Int) -> Unit)
    fun refreshAllConsultChatUnReadCount(call: (Int) -> Unit)
    fun cleanChatUnReadCount(userName: String)
    fun cleanAllChatUnReadCount()
    fun createConversationListFragment(call: (Fragment) -> Unit)
    fun createPiazzaConversationListFragment(call: (Fragment) -> Unit)
    fun createDoctorAndUserConsultConversationListFragment(call: (Fragment) -> Unit)
    fun createChatForPiazza(toUserName: String, call: (Boolean) -> Unit)
    fun createChatForDoctor(toUserName: String, question_id: Int, call: (Boolean) -> Unit)
    fun logoutIm()
}