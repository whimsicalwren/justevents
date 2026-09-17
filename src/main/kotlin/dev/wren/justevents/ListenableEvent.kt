package dev.wren.justevents

import java.util.function.Consumer
import java.util.function.Predicate

interface ListenableEvent<out T> {

    fun on(callback: EventConsumer<T>): EventListener

    fun on(callback: Consumer<in T>): EventListener {
        return on { value, _ -> callback.accept(value) }
    }

    fun on(callback: EventConsumer<T>, condition: Predicate<in T>): EventListener {
        return on { value, listener ->
            if (condition.test(value)) {
                callback.accept(value, listener)
            }
        }
    }

    fun on(callback: Consumer<in T>, condition: Predicate<in T>): EventListener {
        return on { value, _ ->
            if (condition.test(value)) {
                callback.accept(value)
            }
        }
    }

    fun once(callback: Consumer<in T>): EventListener {
        return on { value, listener ->
            callback.accept(value)
            listener.unregister()
        }
    }


    fun once(callback: Consumer<in T>, condition: Predicate<in T>): EventListener {
        return on { value, handler ->
            if (condition.test(value)) {
                callback.accept(value)
                handler.unregister()
            }
        }
    }

}