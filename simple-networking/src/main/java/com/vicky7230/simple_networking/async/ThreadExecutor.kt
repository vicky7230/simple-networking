package com.vicky7230.simple_networking.async

import java.util.concurrent.Executors

object ThreadExecutor {
    private val executorService = Executors.newCachedThreadPool()

    fun execute(task: Runnable) {
        executorService.submit(task)
    }
}