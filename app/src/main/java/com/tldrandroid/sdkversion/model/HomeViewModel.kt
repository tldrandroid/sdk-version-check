package com.tldrandroid.sdkversion.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.tldrandroid.sdkversion.MainActivity
import com.tldrandroid.sdkversion.R
import com.tldrandroid.sdkversion.di.BuildVersionModule
import com.tldrandroid.sdkversion.ext.requiresNotificationPermissions
import com.tldrandroid.sdkversion.ext.supportsMutablePendingIntents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Named(BuildVersionModule.SDK_INT) private val sdkInt: Int
) : ViewModel() {
    private companion object {
        private const val CHANNEL_ID = "42561"
        private const val STARTING_NOTIFICATION_ID = 15964
    }

    private var notificationId = STARTING_NOTIFICATION_ID

    fun showNotification(activity: ComponentActivity) {
        createNotificationChannel(activity)

        if (hasNotificationPermission(activity)) {
            buildAndShowNotification(activity)
        }
    }

    @SuppressLint("NewApi") // Accounted for via DI
    private fun buildAndShowNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flags = if (sdkInt.supportsMutablePendingIntents()) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, flags)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hello!")
            .setContentText("This is a notification!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("This is a notification!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId++, builder.build())
        }
    }

    @SuppressLint("NewApi") // Accounted for via DI
    private fun createNotificationChannel(context: Context) {
        if (!sdkInt.supportsMutablePendingIntents()) return

        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("NewApi") // Accounted for via DI
    private fun hasNotificationPermission(context: Context): Boolean =
        if (sdkInt.requiresNotificationPermissions()) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
}