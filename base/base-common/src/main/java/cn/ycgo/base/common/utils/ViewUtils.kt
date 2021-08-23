package cn.ycgo.base.common.utils

import android.util.TypedValue
import android.view.View

/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
fun Float.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        ContextProvider.context.resources.displayMetrics
    )
}

fun Int.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        ContextProvider.context.resources.displayMetrics
    )
}

fun Float.dpToPxOffset(): Int {
    return dpToPx().toInt()
}

fun Int.dpToPxOffset(): Int {
    return dpToPx().toInt()
}

fun Int.spToPxOffset(): Int {
    return spToPx().toInt()
}

fun Float.spToPxOffset(): Int {
    return spToPx().toInt()
}

fun Float.spToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(),
        ContextProvider.context.resources.displayMetrics
    )
}

fun Int.spToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(),
        ContextProvider.context.resources.displayMetrics
    )
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInVisible() {
    this.visibility = View.INVISIBLE
}

fun View.getRawY(): Int {
    return IntArray(2).apply {
        getLocationOnScreen(this)
    }[1]
}

fun View.getRawX(): Int {
    return IntArray(2).apply {
        getLocationOnScreen(this)
    }[0]
}

fun View.setViewHeight(view: View, height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.dpToPxOffset()
    view.layoutParams = layoutParams
}

