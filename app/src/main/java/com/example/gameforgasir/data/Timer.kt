package com.example.gameforgasir.data

import com.example.gameforgasir.ONE_SECOND_IN_MILLIS
import com.example.gameforgasir.TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class Timer(
    private val coroutineScope: CoroutineScope,
    startingTimeInSeconds: Long
) {
    private var remainingTimeInSeconds = startingTimeInSeconds
    private var timerJob: Job? = null
    fun start() {
        timerJob = coroutineScope.launch {
            onTick(remainingTimeInSeconds)
            while (remainingTimeInSeconds > 0) {
                delay(ONE_SECOND_IN_MILLIS)
                onTick(--remainingTimeInSeconds)
            }
            onFinish()
        }
    }

    fun stop() {
        timerJob?.cancel()
        remainingTimeInSeconds = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
    }

    fun pause() = timerJob?.cancel()

    fun reset() {
        remainingTimeInSeconds = TIME_DURATION_FOR_EACH_QUESTION_IN_SECONDS
    }

    fun restart() {
        stop()
        start()
    }
    abstract fun onTick(remainingTimeInSeconds: Long)
    abstract fun onFinish()
}