package cn.ycgo.base.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by brian
 * on 2018/5/14.
 * 状态栏跟虚拟按键工具类
 */
object StatusBarUtils {

    /**
     * 设置状态栏文字颜色
     */
    fun setStatusBarTextColor(window: Window, isWhiteText: Boolean) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val decor = window.decorView
        var ui = decor.systemUiVisibility
        if (isWhiteText) {
            //浅
            ui = ui and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            if (Build.MANUFACTURER.equals("Xiaomi", ignoreCase = true) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                setMIUIStateBar(window, false)
            }
            if (Build.MANUFACTURER.equals("Meizu", ignoreCase = true) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                setFlymeStateBar(window, false)
            }
        } else {
            //设置为深
            ui = ui or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (Build.MANUFACTURER.equals("Xiaomi", ignoreCase = true) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                setMIUIStateBar(window, true)
            }
            if (Build.MANUFACTURER.equals("Meizu", ignoreCase = true) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                setFlymeStateBar(window, true)
            }
        }
        decor.systemUiVisibility = ui
    }

    /**
     * 魅族状态栏字体颜色设置
     *
     * @param isFontColorDark
     * @param window
     */
    private fun setFlymeStateBar(window: Window?, isFontColorDark: Boolean) {
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (isFontColorDark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
            } catch (e: Exception) {
            }
        }
    }

    /**
     * MIUI状态栏字体颜色设置
     *
     * @param window
     * @param isFontColorDark
     */
    @SuppressLint("PrivateApi")
    private fun setMIUIStateBar(window: Window?, isFontColorDark: Boolean) {
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                val darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (isFontColorDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * 隐藏虚拟导航栏
     */
    fun hideSystemNavigationBar(act: Activity, ishide: Boolean) {
        if (ishide) {
            val view = act.window.decorView
            view.systemUiVisibility = View.GONE
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val decorView = act.window.decorView
                val option = decorView.systemUiVisibility and (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv() or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                decorView.systemUiVisibility = option
            }
        }
    }
}