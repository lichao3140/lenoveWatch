package cn.ycgo.base.common.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 *Author:Kgstt
 *Time: 2020/12/21
 */
object NewWorkUtil {
    /**
     * 描述：判断网络是否有效.
     *
     * @param context the context
     * @return true, if is network available
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isAvailable ?: false
    }
}