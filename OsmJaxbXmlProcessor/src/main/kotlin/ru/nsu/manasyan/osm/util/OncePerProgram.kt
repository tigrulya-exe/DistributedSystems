package ru.nsu.manasyan.osm.util

import java.util.concurrent.atomic.AtomicBoolean

object OncePerProgram {
    private val done = AtomicBoolean()

    fun run(task: () -> Unit) {
        if (done.get()) return
        if (done.compareAndSet(false, true)) {
            task()
        }
    }
}