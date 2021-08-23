package cn.ycgo.base.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 权限控制
 */
object PermissionUtils {
    private const val CODE_REQUEST_PERMISSION = 1
    fun checkAndRequestPermission(activity: Activity, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkPermission = ContextCompat.checkSelfPermission(activity, permission)
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), CODE_REQUEST_PERMISSION)
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    fun checkAndRequestPermission(activity: Activity, permission: String, requestCode: Int): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkPermission = ContextCompat.checkSelfPermission(activity, permission)
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    fun checkAndRequestPermission(fragment: Fragment, permission: String): Boolean {
        val activity = fragment.activity
        activity ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkPermission = ContextCompat.checkSelfPermission(activity, permission)
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(arrayOf(permission), CODE_REQUEST_PERMISSION)
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    fun checkAndRequestPermission(fragment: Fragment, permission: String, requestCode: Int): Boolean {
        val activity = fragment.activity
        activity ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkPermission = ContextCompat.checkSelfPermission(activity, permission)
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(arrayOf(permission), requestCode)
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    /**
     * 检测在Api23及以上的系统中是否获取了权限
     */
    fun checkPermissionForApi23(context: Context, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkPermission = ContextCompat.checkSelfPermission(context, permission)
            checkPermission == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     * 申请权限
     */
    fun requestPermissionForApi23(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), CODE_REQUEST_PERMISSION)
    }

    /**
     * 申请权限
     */
    fun requestPermissionForApi23(fragment: Fragment, permission: String) {
        fragment.requestPermissions(arrayOf(permission), CODE_REQUEST_PERMISSION)
    }

    /**
     * 申请权限
     */
    fun requestPermissionForApi23(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, CODE_REQUEST_PERMISSION)
    }

    /**
     * 申请权限
     */
    fun requestPermissionForApi23(activity: Activity, permissions: Array<String>, code: Int) {
        ActivityCompat.requestPermissions(activity, permissions, code)
    }

    fun shouldShowRequestPermission(activity: Activity, permissions: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions)
    }

    fun intentToNotificationPermissionSetting(activity: Activity) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                val intent = Intent()
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, activity.applicationInfo.uid)
                activity.startActivity(intent)
            }
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 -> {
                toSystemConfig(activity);
            }
            else -> {
                try {
                    toApplicationInfo(activity);
                } catch (e: Exception) {
                    toSystemConfig(activity);
                }
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    private fun toApplicationInfo(activity: Activity) {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        localIntent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(localIntent)
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    private fun toSystemConfig(activity: Activity) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface PermissionCallBack {
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    }

    interface PermissionCallBackSetter {
        fun setOnRequestPermissionsListener(permissionCallBack: PermissionCallBack)
    }
}