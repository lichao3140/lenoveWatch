package cn.ycgo.base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.LogUtils.IFormatter
import com.blankj.utilcode.util.Utils
import com.ycgo.base.BuildConfig
import me.jessyan.autosize.utils.AutoSizeLog.isDebug
import java.util.*

/**
 *Author:Kgstt
 *Time: 2020/11/23
 */
open class BaseFrameworkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        initLog()
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()// 开启调试模式
            ARouter.openLog()
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    open fun initLog() {
        val config = LogUtils.getConfig()
            .setLogSwitch(isDebug()) // 设置 log 总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(isDebug()) // 设置是否输出到控制台开关，默认开
            .setGlobalTag(null) // 设置 log 全局标签，默认为空
            // 当全局标签不为空时，我们输出的 log 全部为该 tag，
            // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
            .setLogHeadSwitch(true) // 设置 log 头信息开关，默认为开
            .setLog2FileSwitch(false) // 打印 log 时是否存到文件的开关，默认关
            .setDir("") // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("") // 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
            .setBorderSwitch(false) // 输出日志是否带边框开关，默认开
            .setConsoleFilter(LogUtils.D) // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
            .setFileFilter(LogUtils.D) // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
            //                .setStackDeep(1)// log 栈深度，默认为 1
            //                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
            .addFormatter(object : IFormatter<ArrayList<*>>() {
                override fun format(list: ArrayList<*>): String {
                    return "LogUtils Formatter ArrayList { $list }"
                }
            })
            .setFileWriter(null)
        LogUtils.i(config.toString())
    }

}




