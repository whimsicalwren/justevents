package dev.wren.justevents

interface EmittableEvent<in T> {
    fun emit(value: T)
}