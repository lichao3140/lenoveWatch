package cn.ycgo.base.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
internal object BackHandlerHelper {
    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     *
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    private fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments: List<Fragment> = fragmentManager.fragments
        for (i in fragments.indices.reversed()) {
            val child: Fragment = fragments[i]
            if (isFragmentBackHandled(child)) {
                return true
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 **true**
     */
    private fun isFragmentBackHandled(fragment: Fragment): Boolean {
        if (!fragment.isVisible || !fragment.isResumed) { //在本工程不管是viewpager还是show、hide都只需要判断onResume即可
            return false
        }
        if (fragment !is FragmentBackHandler) {
            return false
        }
        return fragment.onBackPressed()
    }
}