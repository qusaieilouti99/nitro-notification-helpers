package com.margelo.nitro.nitronotificationhelpers


object NotificationEventBus {
    private var listener: ((String) -> Unit)? = null

    fun setListener(l: ((String) -> Unit)?) {
        listener = l
    }

    fun emit(notification: String) {
        listener?.invoke(notification)
    }

    fun hasListener(): Boolean = listener != null
}
