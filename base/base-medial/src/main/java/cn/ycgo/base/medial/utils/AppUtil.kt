package cn.ycgo.base.medial.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.FileProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalSpEngine
import java.io.File


/**
 *Author:Kgstt
 *Time: 2020/12/10
 */
object AppUtil {
    /**
     * 打开键盘.
     *
     * @param context the context
     */
    fun showSoftInput(context: Context) {
        val inputMethodManager = (context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 关闭键盘事件.
     */
    fun closeSoftInput(activity: Activity) {
        val inputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!
                    .windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    /**
     * 获取版本号名称 versionName
     *
     * @return
     */
    fun getVerName(): String? {
        var verName: String? = ""
        try {
            verName = ContextProvider.context.packageManager.getPackageInfo(ContextProvider.context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verName
    }

    /**
     * 获取 versionCode
     */
    fun getAppVersion(context: Context): Int {
        val manager = context.packageManager
        var code = 0
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            code = info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }


    /**
     * @param file
     * @return
     * @Description 安装apk
     */
    fun installApk(context: Context, file: File) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /* Android N 写法*/
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val contentUri: Uri = FileProvider.getUriForFile(context, ContextProvider.context.packageName + ".fileProvider", file)
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                /* Android N之前的老版本写法*/
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, "安装出错", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 获取应用程序名称
     */
    fun getAppName(): String? {
        try {
            val packageManager = ContextProvider.context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                ContextProvider.context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            return ContextProvider.context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取图标 bitmap
     * @param context
     */
    @Synchronized
    fun getIconBitmap(context: Context): Bitmap? {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo? = null
        try {
            packageManager = context.applicationContext
                .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                context.packageName, 0
            )
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }
        val d = packageManager!!.getApplicationIcon(applicationInfo!!) //xxx根据自己的情况获取drawable
        val bd = d as BitmapDrawable
        return bd.bitmap
    }

    /**
     * 根据包名判断是否安装了Apk
     */
    fun checkAppInstalled(context: Context, packageName: String?): Boolean {
        var isInstalled = false
        if (packageName == null || packageName.isEmpty()) {
            isInstalled = false
        }
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(packageName!!, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo != null) {
            isInstalled = true
        }
        return isInstalled
    }

    /**
     * 防止重复点击
     */
    private const val MIN_DOUBLE_TIME: Long = 1000
    private var lastClickTime: Long = 0
    fun onDoubleClickSynchronized(hit: (Boolean) -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()
        lastClickTime = if (currentTimeMillis - lastClickTime < MIN_DOUBLE_TIME) {
            hit.invoke(true)
            0
        } else {
            hit.invoke(false)
            currentTimeMillis
        }
    }

    /**
     * 防止重复点击
     */
    private const val MIN_DELAY_TIME: Long = 600
    private var temp: Long = 0
    fun onClickSynchronized(hit: () -> Unit) {
        if (System.currentTimeMillis() - temp >= MIN_DELAY_TIME) {
            hit.invoke()
        }
        temp = System.currentTimeMillis()
    }

    /**
     * 登录过滤
     */
    fun loginJumpFilter(visitorFlag: () -> Unit) {
        if (GlobalSpEngine.isVisitor()) {
            ARouter.getInstance().build(PathConstants.ACTIVITY_LOGIN).navigation()
        } else {
            visitorFlag.invoke()
        }
    }

    //    @param phoneNum 电话号码
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:" + phoneNum)
        intent.data = data
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        ContextProvider.context.startActivity(intent)
    }

    /***
     * Bean 到 String 的序列化
     */
    fun bean2Str(obj: Any?): String {
        return try {
            Gson().toJson(obj)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            "{}"
        }
    }

    /**
     * String 到 Bean 的反序列化
     */
    fun <T> str2Bean(string: String?, clazz: Class<T>?): T? {
        return try {
            Gson().fromJson(string, clazz)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
}