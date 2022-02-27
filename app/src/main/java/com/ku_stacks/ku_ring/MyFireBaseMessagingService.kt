package com.ku_stacks.ku_ring

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ku_stacks.ku_ring.data.db.PushDao
import com.ku_stacks.ku_ring.data.db.PushEntity
import com.ku_stacks.ku_ring.ui.detail.DetailActivity
import com.ku_stacks.ku_ring.ui.home.HomeActivity
import com.ku_stacks.ku_ring.util.DateUtil
import com.ku_stacks.ku_ring.util.UrlGenerator
import com.ku_stacks.ku_ring.util.WordConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyFireBaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var pushDao : PushDao

    override fun onNewToken(token: String){
        Timber.e("refreshed token : $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (isNoticeNotification(remoteMessage)) {
            val articleId = remoteMessage.data["articleId"]!!
            val categoryEng = remoteMessage.data["category"]!!
            val postedDate = remoteMessage.data["postedDate"]!!
            val subject = remoteMessage.data["subject"]!!
            val baseUrl = remoteMessage.data["baseUrl"]!!

            // insert into db
            val categoryKr = WordConverter.convertEnglishToKorean(categoryEng)
            val receivedDate = DateUtil.getCurrentTime()
            insertNotificationIntoDatabase(articleId, categoryKr, postedDate, subject, baseUrl, receivedDate)

            // show notification
            val webUrl = UrlGenerator.generateNoticeUrl(articleId = articleId, category = categoryKr, baseUrl = baseUrl)
            showNotificationWithUrl(title = subject, body = categoryKr, url = webUrl)
        } else if (isCustomNotification(remoteMessage)) {
            val type = remoteMessage.data["type"]!!
            val title = remoteMessage.data["title"]!!
            val body = remoteMessage.data["body"]!!

            // show notification
            showNotification(type = type, title = title, body = body)
        }
    }

    private fun isNoticeNotification(remoteMessage: RemoteMessage): Boolean {
        val articleId = remoteMessage.data["articleId"]
        val categoryEng = remoteMessage.data["category"]
        val postedDate = remoteMessage.data["postedDate"]
        val subject = remoteMessage.data["subject"]
        val baseUrl = remoteMessage.data["baseUrl"]

        return articleId != null && categoryEng != null && postedDate != null
                && subject != null && baseUrl != null
    }

    private fun isCustomNotification(remoteMessage: RemoteMessage): Boolean {
        val customTypeSet = setOf("admin")

        val type = remoteMessage.data["type"]?.lowercase()
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        return type != null && title != null && body != null && customTypeSet.contains(type)
    }

    private fun insertNotificationIntoDatabase(
        articleId: String,
        category: String,
        postedDate: String,
        subject: String,
        baseUrl: String,
        receivedDate: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                pushDao.insertNotification(
                    PushEntity(
                        articleId = articleId,
                        category = category,
                        postedDate = postedDate,
                        subject = subject,
                        baseUrl = baseUrl,
                        isNew = true,
                        receivedDate = receivedDate
                    )
                )
                Timber.e("insert notification success")
            } catch (e: Exception) {
                Timber.e("insert notification error : $e")
            }
        }
    }

    private fun showNotificationWithUrl(title: String?, body: String?, url: String?){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(HomeActivity.NOTICE_URL, url)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = "ku_stack_channel_id"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_ku_ring_notification))
            .setSmallIcon(R.drawable.ic_ku_ring_statusbar)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "쿠링",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun showNotification(type: String, title: String, body: String) {
        val intent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = "ku_stack_channel_id"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_ku_ring_notification))
            .setSmallIcon(R.drawable.ic_ku_ring_statusbar)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "쿠링",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
}