package com.example.appchui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import kotlin.concurrent.thread

class MyObserver : Application(), LifecycleObserver {

    var titles: Array<String> = arrayOf("Vãi lồn",
        "Chó",
        "Lợn",
        "Làm việc đi")
    var messages: Array<String> = arrayOf("Bỏ điện thoại xuống",
        "Tập trung vào",
        "Làm việc đi cho đỡ vô dụng",
        "Không làm việc thì làm chó")

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }

    @Volatile var pause: Boolean = true

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackgrounded() {
        //App in background
        pause = true
        thread {
            while (pause) {
                for (i in 0..3) {
                    handleNotification(titles[i],messages[i])
                    Thread.sleep(3_000)
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForegrounded() {
        Log.d("hi", "false")
        pause = false
    }

    private fun handleNotification(tString: String, mString: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "0")
            .setSmallIcon(R.drawable.baseline_settings_black_18dp)
            .setContentTitle(tString)
            .setContentText(mString)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }

}