package me.woodyn.myklas.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class FullLectureArchive {
    private val fullLectureIds: MutableSet<Long> = ConcurrentHashMap.newKeySet()

    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    fun isFull(lectureId: Long) =
        lectureId in fullLectureIds

    @Async
    fun setFullFor(lectureId: Long, duration: Long) {
        fullLectureIds += lectureId
        logger.debug("Set lecture $lectureId full")

        Thread.sleep(duration)
        fullLectureIds -= lectureId
        logger.debug("Invalidate capacity cache of lecture $lectureId")
    }

}