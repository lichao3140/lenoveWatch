package cn.ycgo.base.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import cn.ycgo.base.common.storage.CommonSPEngine
import cn.ycgo.base.common.utils.ActivityStack
import me.jessyan.autosize.AutoSizeConfig


/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
internal class CommonApplication {

    fun onCreate(appContext: Context) {

        AutoSizeConfig.getInstance().isExcludeFontScale = true

        (appContext.applicationContext as? Application)?.run {
            registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity) {
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityDestroyed(activity: Activity) {
                    ActivityStack.remove(activity)
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    ActivityStack.push(activity)
                }

                override fun onActivityResumed(activity: Activity) {
                }
            })
        }

        DebugConfig.setClientType(CommonSPEngine.getDebugType())
    }
}