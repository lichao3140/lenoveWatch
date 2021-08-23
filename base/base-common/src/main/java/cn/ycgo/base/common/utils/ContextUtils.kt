package cn.ycgo.base.common.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * Author:Kgstt
 * Time: 2020/11/23
 * 通过Context获取Activity，当然appContext无法获取到Activity
 */
fun Context.getActivity(): Activity? {
    if (this is Activity) {
        return this
    }
    if (this is ContextWrapper) {
        return if (this is Activity) {
            this
        } else {
            baseContext.getActivity()
        }
    }
    return null
}

fun Context.getAppCompatActivity(): AppCompatActivity? {
    if (this is AppCompatActivity) {
        return this
    }
    if (this is ContextWrapper) {
        return if (this is AppCompatActivity) {
            this
        } else {
            baseContext.getAppCompatActivity()
        }
    }
    return null
}

@ColorInt
fun Context.getCompatColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(ContextProvider.context, id)
}

fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(ContextProvider.context, id)
}

/**
 * 设置当前屏幕亮度，不修改系统值
 */
fun Context.setBrightness(value: Int) {
    val activity = getActivity() ?: return
    val window = activity.window
    val params = window.attributes
    val f = if (value < 0) WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE else value / 255.0f
    params.screenBrightness = f
    window.attributes = params
}