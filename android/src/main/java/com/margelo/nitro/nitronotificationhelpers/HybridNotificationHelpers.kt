package com.margelo.nitro.nitronotificationhelpers

import android.util.Log
import com.facebook.proguard.annotations.DoNotStrip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@DoNotStrip
class HybridNotificationHelpers : HybridNitroNotificationHelpersSpec() {

    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val TAG = "HybridNotificationHelpers"

    // single active listener stored on the HybridObject instance
    private var activeListener: ((String) -> Unit)? = null

    override fun addListener(listener: (notification: String) -> Unit) {
        // Wrap the JS callback in a safe Kotlin lambda to avoid exceptions escaping native boundary
        val safeListener: (String) -> Unit = { n ->
            try {
                listener(n)
            } catch (e: Exception) {
                Log.e(TAG, "JS listener threw", e)
            }
        }

        activeListener = safeListener
        NotificationEventBus.setListener(safeListener)
        Log.d(TAG, "Listener added.")
        // NotificationEventBus will dispatch any cached notifications upon setListener()
    }

    override fun removeListener() {
        Log.d(TAG, "removeListener called.")
        activeListener = null
        NotificationEventBus.setListener(null)
    }

    override fun getInitialClickedNotification(): String? {
        // Pop one cached notification if any (and remove it from the queue)
        return NotificationEventBus.popCachedNotification()
    }

    override fun cleanUpStoreNotifications() {
        NotificationEventBus.clearCachedNotifications()
    }
}