package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class MaxCreditRegisterConstraintTest {

    @MockK
    private lateinit var registrationRepository: LectureRegistrationRepository

    @Test
    fun `Comply when not exceeding the credit limit`() {
        val student = aStudent()

        every { registrationRepository.findRegistrationsBelongToTerm(any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(credit = 3)
                    ),
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(credit = 3)
                    )
                )
            )

        val constraint = MaxCreditRegisterConstraint(registrationRepository, 10)
        val result = constraint.comply(student, aLecture(credit = 3))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when exceeding the credit limit`() {
        val student = aStudent()

        every { registrationRepository.findRegistrationsBelongToTerm(any(), any()) }
            .returns(
                setOf(
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(credit = 3)
                    ),
                    LectureRegistration(
                        student = student,
                        lecture = aLecture(credit = 3)
                    )
                )
            )

        val constraint = MaxCreditRegisterConstraint(registrationRepository, 8)
        val result = constraint.comply(student, aLecture(credit = 3))

        result.shouldBeFalse()
    }

}