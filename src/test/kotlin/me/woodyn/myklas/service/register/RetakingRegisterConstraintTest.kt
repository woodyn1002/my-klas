package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class RetakingRegisterConstraintTest {

    @MockK
    private lateinit var registrationRepository: LectureRegistrationRepository

    @Test
    fun `Comply when the lecture hasn't been taken ever`() {
        val student = aStudent()

        every { registrationRepository.findOldRegistrationsBySubject(any(), any(), any()) }
            .returns(emptySet())

        val constraint = RetakingRegisterConstraint(registrationRepository, maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when the lecture has been taken with the grade which exceeds the limit`() {
        val student = aStudent()

        every { registrationRepository.findOldRegistrationsBySubject(any(), any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(term = "2019-2", subject = "algorithm"),
                        grade = Grade.B_ZERO
                    )
                )
            )

        val constraint = RetakingRegisterConstraint(registrationRepository, maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

    @Test
    fun `Doesn't comply when the lecture has been retaken already`() {
        val student = aStudent()

        every { registrationRepository.findOldRegistrationsBySubject(any(), any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(term = "2019-2", subject = "algorithm"),
                        grade = Grade.F
                    ),
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(term = "2020-1", subject = "algorithm"),
                        grade = Grade.A_ZERO
                    )
                )
            )

        val constraint = RetakingRegisterConstraint(registrationRepository, maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

}