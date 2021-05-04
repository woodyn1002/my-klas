package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureRegistration
import me.woodyn.myklas.helper.withSchedule
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

internal class ValidTimetableCheckerTest {
    @Test
    fun `Comply when no lectures conflict`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(term = "2021-1")
                    .withSchedule(
                        dayOfWeek = DayOfWeek.MONDAY,
                        beginTime = LocalTime.of(12, 0),
                        endTime = LocalTime.of(13, 15)
                    )
                    .withSchedule(
                        dayOfWeek = DayOfWeek.WEDNESDAY,
                        beginTime = LocalTime.of(13, 30),
                        endTime = LocalTime.of(14, 45)
                    )
            )
        )

        val checker = ValidTimetableChecker()
        val newLecture = aLecture(term = "2021-1")
            .withSchedule(
                dayOfWeek = DayOfWeek.THURSDAY,
                beginTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(13, 15)
            )
            .withSchedule(
                dayOfWeek = DayOfWeek.WEDNESDAY,
                beginTime = LocalTime.of(14, 45),
                endTime = LocalTime.of(15, 0)
            )
        val result = checker.check(registrations, newLecture)

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when some lectures conflict each other`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(term = "2021-1")
                    .withSchedule(
                        dayOfWeek = DayOfWeek.MONDAY,
                        beginTime = LocalTime.of(12, 0),
                        endTime = LocalTime.of(13, 15)
                    )
                    .withSchedule(
                        dayOfWeek = DayOfWeek.WEDNESDAY,
                        beginTime = LocalTime.of(13, 30),
                        endTime = LocalTime.of(14, 45)
                    )
            )
        )

        val checker = ValidTimetableChecker()
        val newLecture = aLecture(term = "2021-1")
            .withSchedule(
                dayOfWeek = DayOfWeek.WEDNESDAY,
                beginTime = LocalTime.of(13, 0),
                endTime = LocalTime.of(14, 15)
            )
        val result = checker.check(registrations, newLecture)

        result.shouldBeFalse()
    }

}