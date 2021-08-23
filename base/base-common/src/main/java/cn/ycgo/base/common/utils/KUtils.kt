package cn.ycgo.base.common.utils

import java.util.regex.Pattern

/**
 * ```
 * author: Kgstt
 * time  : 6/3/21 11:12 AM
 * ```
 */
object KUtils {
    fun isNumber(string: String?): Boolean {
        if (string == null) return false
        val pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$")
        return pattern.matcher(string).matches()
    }
}