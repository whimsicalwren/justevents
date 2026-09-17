package dev.wren.justevents

import java.util.concurrent.CopyOnWriteArraySet

class GenericEvent<T> : ListenableEvent<T>, EmittableEvent<T> {

    private val listeners = CopyOnWriteArraySet<ThisEventListener>()

    override fun emit(value: T) {
        listeners.forEach { it.accept(value) }
    }

    override fun on(callback: EventConsumer<T>): EventListener {
        val listener = ThisEventListener(callback)
        listeners.add(listener)
        return listener
    }

    private inner class ThisEventListener(private val cb: EventConsumer<T>) : EventListener {
        fun accept(event: T) = cb.accept(event, this)
        override fun unregister() {
            listeners.remove(this)
        }
    }
}