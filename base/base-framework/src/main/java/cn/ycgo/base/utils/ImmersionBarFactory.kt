package cn.ycgo.base.utils

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
object ImmersionBarFactory {
    @ColorRes
    private var statusBarColor: Int = 0

    @ColorRes
    private var navigationBarColor: Int = 0

    fun init(@ColorRes statusBarColor: Int, @ColorRes navigationBarColor: Int) {
        ImmersionBarFactory.statusBarColor = statusBarColor
        ImmersionBarFactory.navigationBarColor = navigationBarColor
    }

    fun default(activity: Activity): ImmersionBar {
        return ImmersionBar.with(activity).setDefault()
    }

    fun default(dialog: DialogFragment): ImmersionBar {
        return ImmersionBar.with(dialog).setDefault()
    }

    fun default(fragment: Fragment): ImmersionBar {
        return ImmersionBar.with(fragment).setDefault()
    }

    private fun ImmersionBar.setDefault(): ImmersionBar {
        if (statusBarColor != 0) {
            statusBarColor(statusBarColor)
        }
        if (navigationBarColor != 0) {
            navigationBarColor(navigationBarColor)
        }
        //不能用autoDarkModeEnable，否则透明状态栏会呈现白色字，和默认背景冲突
        statusBarDarkFont(true)
        navigationBarDarkIcon(true)
        return this
    }
}