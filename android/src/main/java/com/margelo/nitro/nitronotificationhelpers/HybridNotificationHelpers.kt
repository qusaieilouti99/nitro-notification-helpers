package com.margelo.nitro.nitronotificationhelpers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.Keep
import com.facebook.jni.HybridData
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.NitroModules

@DoNotStrip
@Keep
class HybridNotificationHelpers : HybridNitroNotificationHelpersSpec() {

    private val prefs: SharedPreferences
        get() = NitroModules.applicationContext!!
            .getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    override fun addListener(listener: (notification: String) -> Unit): () -> Unit {
        NotificationEventBus.setListener { notification ->
            listener(notification)
        }
        // Check persistent storage for a pending notification
        val stored = prefs.getString("initial_notification", null)
        if (stored != null) {
            listener(stored)
            prefs.edit().remove("initial_notification").apply()
        }
        return {
            NotificationEventBus.setListener(null)
        }
    }

    override fun removeListeners(): () -> Unit {
        NotificationEventBus.setListener(null)
        return {}
    }

    override fun getInitialClickedNotification(): String? {
        val notification = prefs.getString("initial_notification", null)
        prefs.edit().remove("initial_notification").apply()
        return notification
    }

    override fun storeNotification(notification: String) {
        if (NotificationEventBus.hasListener()) {
            NotificationEventBus.emit(notification)
        } else {
            prefs.edit().putString("initial_notification", notification).apply()
        }
    }
}
