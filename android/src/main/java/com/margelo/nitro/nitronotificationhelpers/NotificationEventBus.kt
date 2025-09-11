package com.margelo.nitro.nitronotificationhelpers

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.ArrayDeque

object NotificationEventBus {
    private const val TAG = "NotificationEventBus"
    private val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val lock = Any()
    private var listener: ((String) -> Unit)? = null
    private val cache = ArrayDeque<String>()

    /**
     * Set or clear the active callback. If a non-null listener is provided,
     * dispatch all cached notifications to it (one by one) on the main thread.
     */
    fun setListener(l: ((String) -> Unit)?) {
        synchronized(lock) {
            listener = l
            if (l != null) {
                // Drain cache and dispatch each on the main thread
                while (cache.isNotEmpty()) {
                    val n = cache.removeFirst()
                    mainScope.launch {
                        try {
                            l(n)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error dispatching cached notification", e)
                        }
                    }
                }
            }
        }
    }

    /**
     * Emit a notification. If a listener is active, invoke it on the main thread.
     * Otherwise, store the notification in the queue.
     */
    fun emit(notification: String) {
        val current = synchronized(lock) { listener }
        if (current != null) {
            mainScope.launch {
                try {
                    current(notification)
                } catch (e: Exception) {
                    Log.e(TAG, "Error invoking notification listener", e)
                }
            }
        } else {
            synchronized(lock) {
                cache.addLast(notification)
            }
        }
    }

    fun hasListener(): Boolean = synchronized(lock) { listener != null }

    /**
     * Pop (and return) one cached notification, or null if none.
     * Useful for getInitialClickedNotification semantics.
     */
    fun popCachedNotification(): String? = synchronized(lock) {
        if (cache.isEmpty()) null else cache.removeFirst()
    }

    fun clearCachedNotifications() = synchronized(lock) {
        cache.clear()
    }
}