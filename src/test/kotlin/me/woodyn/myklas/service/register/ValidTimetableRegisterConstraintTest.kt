package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.withSchedule
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.DayOfWeek
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
internal class ValidTimetableRegisterConstraintTest {

    @MockK
    private lateinit var registrationRepository: LectureRegistrationRepository

    @Test
    fun `Comply when no lectures conflict`() {
        val student = aStudent()

        every { registrationRepository.findRegistrationsBelongToTerm(any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
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
            )

        val constraint = ValidTimetableRegisterConstraint(registrationRepository)
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
        val result = constraint.comply(student, newLecture)

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when some lectures conflict each other`() {
        val student = aStudent()

        every { registrationRepository.findRegistrationsBelongToTerm(any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
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
            )

        val constraint = ValidTimetableRegisterConstraint(registrationRepository)
        val newLecture = aLecture(term = "2021-1")
            .withSchedule(
                dayOfWeek = DayOfWeek.WEDNESDAY,
                beginTime = LocalTime.of(13, 0),
                endTime = LocalTime.of(14, 15)
            )
        val result = constraint.comply(student, newLecture)

        result.shouldBeFalse()
    }

}