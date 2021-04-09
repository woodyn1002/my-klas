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
internal class NoSameSubjectRegisterConstraintTest {

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
                        lecture = aLecture(term = "2021-1", subject = "java programming")
                    )
                )
            )

        val constraint = NoSameSubjectRegisterConstraint(registrationRepository)
        val result = constraint.comply(
            student, aLecture(
                term = "2021-1",
                subject = "algorithm"
            )
        )

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
                        lecture = aLecture(term = "2021-1", subject = "algorithm")
                    )
                )
            )

        val constraint = NoSameSubjectRegisterConstraint(registrationRepository)
        val result = constraint.comply(
            student, aLecture(
                term = "2021-1",
                subject = "algorithm"
            )
        )

        result.shouldBeFalse()
    }

}