package cn.ycgo.base.medial.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

/**
 *Author:Kgstt
 *Time: 2020/12/8
 */
object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun formatDateTime(dateL: Long, format: String?): String? {
        val sdf = SimpleDateFormat(format)
        return sdf.format(Date(dateL))
    }

    interface DateFormatStatus {
        companion object {
            const val 月日_时分 = "MM.dd HH:mm"
            const val 年月日_时分秒 = "yyyy-MM-dd HH:mm:ss"
            const val 年月日_时分 = "yyyy-MM-dd HH:mm"
            const val 年月日 = "yyyy-MM-dd"
            const val 月日 = "MM-dd"
            const val 月日_中文 = "MM月dd日"
            const val 年月日_中文 = "yyyy年MM月dd日"
            const val 年月日_点 = "yyyy.MM.dd"
            const val 时分 = "HH:mm"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun timeCompare(timeMillis: Long): String? {
        val dfs = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val data = dfs.format(Date())
        try {
            val end = dfs.parse(data)
            val between = (end.time - timeMillis) / 1000 //除以1000是为了转换成秒
            val day1 = between / (24 * 3600)
            val hour1 = between / 3600
            val minute1 = between / 60
            return if (minute1 < 0) {
                when {
                    minute1 > -60 -> {
                        "${minute1.inv() + 1}分钟后"
                    }
                    hour1 > -24 -> {
                        "${hour1.inv() + 1}小时后"
                    }
                    else -> {
                        formatDateTime(timeMillis, DateFormatStatus.年月日)
                    }
                }
            } else {
                when {
                    minute1 == 0L -> {
                        "刚刚"
                    }
                    minute1 < 60 -> {
                        minute1.toString() + "分钟前"
                    }
                    hour1 < 24 -> {
                        hour1.toString() + "小时前"
                    }
                    day1 < 31 -> {
                        day1.toString() + "天前"
                    }
                    else -> {
                        formatDateTime(timeMillis, DateFormatStatus.年月日)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return formatDateTime(timeMillis, DateFormatStatus.年月日)
    }

    @Throws(Exception::class)
    fun strFormatTimeToLong(strTime: String, strFormat: String?): Long? {
        val timeText = strTime.trim { it <= ' ' }
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat(strFormat)
        var theDate = Date()
        theDate = formatter.parse(timeText)
        return theDate.time
    }

    fun getWeek(timeStamp: Long): String? {
        var mydate = 0
        val cd: Calendar = Calendar.getInstance()
        cd.time = Date(timeStamp)
        mydate = cd.get(Calendar.DAY_OF_WEEK)
        // 获取指定日期转换成星期几
        return when (mydate) {
            1 -> "周日"
            2 -> "周一"
            3 -> "周二"
            4 -> "周三"
            5 -> "周四"
            6 -> "周五"
            7 -> "周六"
            else -> "未知"
        }
    }

}