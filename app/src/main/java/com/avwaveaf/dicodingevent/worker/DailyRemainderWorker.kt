package com.avwaveaf.dicodingevent.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.avwaveaf.dicodingevent.R
import com.avwaveaf.dicodingevent.data.remote.response.EventItem
import com.avwaveaf.dicodingevent.data.remote.retrofit.ApiConfig
import com.avwaveaf.dicodingevent.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyRemainderWorker(context: Context, workParams: WorkerParameters) : CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        return try {
            // Fetch the active event
            val apiService = ApiConfig.getApiService()
            val response = withContext(Dispatchers.IO) {
                apiService.getActiveEvents().execute()
            }

            val events = response.body()

            if (events!=null && events.listEvents.isNotEmpty()) {
                val event = events.listEvents.first()
                sendNotification(event)
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("DailyRemainderWorker", "Error fetching events: ${e.message}")
            Result.failure()
        }
    }

    private fun sendNotification(event: EventItem) {
        val detailIntent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("eventId", event.id.toString())
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            detailIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Upcoming Event")
            .setContentText("Don't miss: ${event.name} at ${event.beginTime}!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val CHANNEL_ID = "daily_reminder_channel"
        const val NOTIFICATION_ID = 1
    }
}
