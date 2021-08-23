package cn.ycgo.base.common.utils

import android.app.Activity
import java.util.*

/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
object ActivityStack {
    private val stack = Stack<Activity>()

    /**
     * 入栈
     */
    fun push(activity: Activity) {
        ActivityStack.stack.push(activity)
    }

    /**
     * 移除指定Activity出栈
     */
    fun remove(activity: Activity) {
        ActivityStack.stack.remove(activity)
    }

    /**
     * 移除除栈顶外的其余元素
     */
    fun <T : Activity> removeExcludeOne(cls: Class<T>) {
        var excludeActivity: Activity? = null
        for (activity in ActivityStack.stack) {
            if (activity.javaClass.name == cls.name) {
                excludeActivity = activity
                continue
            }
            activity.finish()
        }
        ActivityStack.stack.clear()
        excludeActivity?.run {
            ActivityStack.stack.push(this)
        }
    }

    /**
     * 获取栈顶的Activity
     */
    fun peek(): Activity {
        return ActivityStack.stack.peek()
    }

    fun <T : Activity> peek(clazz: Class<T>): Activity? {
        for (activity in ActivityStack.stack) {
            if (activity.javaClass.name == clazz.name) {
                return activity
            }
        }
        return null
    }


}