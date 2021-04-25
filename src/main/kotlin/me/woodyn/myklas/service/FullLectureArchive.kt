package me.woodyn.myklas.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Clock
import java.util.concurrent.ConcurrentHashMap

@Component
class FullLectureArchive {
    private val fullLectureIds: MutableMap<Long, Long> = ConcurrentHashMap()
    private val clock: Clock = Clock.systemDefaultZone()

    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    fun isFull(lectureId: Long): Boolean {
        val expTime = fullLectureIds[lectureId] ?: return false
        if (expTime <= clock.millis()) {
            val removed = fullLectureIds.remove(lectureId, expTime)
            if (removed) {
                logger.debug("Invalidate capacity cache of lecture $lectureId")
            }
            return !removed
        }
        return true
    }

    fun setFullFor(lectureId: Long, duration: Long) {
        val expTime = clock.millis() + duration
        fullLectureIds[lectureId] = expTime
        logger.debug("Set lecture $lectureId full")
    }

}