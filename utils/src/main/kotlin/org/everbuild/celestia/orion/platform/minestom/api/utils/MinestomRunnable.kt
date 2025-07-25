package org.everbuild.celestia.orion.platform.minestom.api.utils

import net.minestom.server.timer.ExecutionType
import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule
import org.everbuild.celestia.orion.platform.minestom.api.Mc
import java.time.Duration

/**
 * A utility to make it easier to build runnables
 *
 * @author emortal
 * @author DasLixou
 */
abstract class MinestomRunnable : Runnable {
    private var task: Task? = null
    private var delaySchedule: TaskSchedule = TaskSchedule.immediate()
    private var repeatSchedule: TaskSchedule = TaskSchedule.stop()
    private var executionType: ExecutionType = ExecutionType.TICK_START

    constructor(delay: Duration, repeat: Duration, executionType: ExecutionType) {
        delay(delay)
        repeat(repeat)
        executionType(executionType)
    }

    constructor(delay: TaskSchedule = TaskSchedule.immediate(), repeat: TaskSchedule = TaskSchedule.stop(), executionType: ExecutionType = ExecutionType.TICK_START) {
        delay(delay)
        repeat(repeat)
        executionType(executionType)
    }

    fun delay(duration: Duration) = this.also { delaySchedule = if(duration != Duration.ZERO) TaskSchedule.duration(duration) else TaskSchedule.immediate() }
    fun delay(schedule: TaskSchedule) = this.also { delaySchedule = schedule }

    fun repeat(duration: Duration) = this.also { repeatSchedule = if(duration != Duration.ZERO) TaskSchedule.duration(duration) else TaskSchedule.stop() }
    fun repeat(schedule: TaskSchedule) = this.also { repeatSchedule = schedule }

    fun executionType(type: ExecutionType) = this.also { executionType = type }

    fun schedule(): Task {
        val t = Mc.scheduler.buildTask(this)
            .let { if (delaySchedule != TaskSchedule.immediate()) it.delay(delaySchedule) else it }
            .let { if (repeatSchedule != TaskSchedule.stop()) it.repeat(repeatSchedule) else it }
            .let { if (executionType != ExecutionType.TICK_START) it.executionType(executionType) else it }
            .schedule()
        this.task = t
        return t
    }

    fun cancel() = task?.cancel()
}