package cn.ycgo.base.medial.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.R

/**
 *Author:Kgstt
 *Time: 21-4-22
 */
class NotificationUtil {
    /**
     * 创建渠道
     */
    private fun createNotificationChannel(channelID: String, channelNAME: String, level: Int): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager: NotificationManager? = ContextProvider.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
            val channel = NotificationChannel(channelID, channelNAME, level)
            //开启指示灯
            channel.enableLights(true)
            //开启震动
            channel.enableVibration(true)
            //设置锁屏展示
            channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            manager?.createNotificationChannel(channel)
            channelID
        } else {
            ""
        }
    }

    fun createNotification(context: Context, intent: Intent, title: String, subTitle: String, icon: String, notificationId: Int, channelID: String, channelNAME: String) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val  stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(intent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingIntent: PendingIntent =resultPendingIntent?: PendingIntent.getActivity(context, 0, intent, 0)
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel(channelID, channelNAME, NotificationManager.IMPORTANCE_HIGH)
        } else {
            channelID
        }
        ImageLoadUtil.loadPhotoToBitmap(
            context = ContextProvider.context, uri = Uri.parse(icon),
            overrideH = 150, overrideW = 150, isCrap = true, place = R.drawable.ic_app_small_transparency
        ) {
            val notification = NotificationCompat.Builder(context, channelId!!)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setLargeIcon(it)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_app_small_transparency)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, notification.build())
        }
    }

    fun cleanNotification(context: Context, notificationId: Int) {
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(notificationId)
    }

}