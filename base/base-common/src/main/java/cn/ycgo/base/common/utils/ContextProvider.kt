package cn.ycgo.base.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.Window
import androidx.annotation.*
import androidx.core.content.ContextCompat


/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
@SuppressLint("StaticFieldLeak")
object ContextProvider {
    lateinit var context: Context
        internal set

    fun getString(@StringRes id: Int): String {
        return ContextProvider.context.getString(id)
    }

    @ColorInt
    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(ContextProvider.context, id)
    }

    fun getColorStateList(@ColorRes id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(ContextProvider.context, id)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(ContextProvider.context, id)
    }

    fun getDisplayWidth(): Int {
        return ContextProvider.context.resources.displayMetrics.widthPixels
    }

    fun getDisplayHeight(): Int {
        return ContextProvider.context.resources.displayMetrics.heightPixels
    }

    fun getRealDisplayHeight(): Int {
        val defaultDisplay = ActivityStack.peek().display
        val metrics = DisplayMetrics()
        defaultDisplay?.getRealMetrics(metrics)
        return metrics.heightPixels
    }

    fun setStatusBarTextColor(window: Window, isWhiteText: Boolean) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StatusBarUtils.setStatusBarTextColor(window, isWhiteText)
        }
    }
}