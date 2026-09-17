package dev.wren.justevents

fun interface EventConsumer<in T> {
    fun accept(event: T, listener: EventListener)
}