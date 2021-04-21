package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.withRegistration
import me.woodyn.myklas.helper.withRegistrations
import me.woodyn.myklas.persistence.model.Grade
import org.junit.jupiter.api.Test

internal class RetakingRegisterConstraintTest {

    @Test
    fun `Comply when the lecture hasn't been taken ever`() {
        val student = aStudent()
            .withRegistrations(emptySet())

        val constraint = RetakingRegisterConstraint(maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when the lecture has been taken with the grade which exceeds the limit`() {
        val student = aStudent()
            .withRegistration(
                lecture = aLecture(term = "2019-2", subject = "algorithm"),
                grade = Grade.B_ZERO
            )

        val constraint = RetakingRegisterConstraint(maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

    @Test
    fun `Doesn't comply when the lecture has been retaken already`() {
        val student = aStudent()
            .withRegistration(
                lecture = aLecture(term = "2019-2", subject = "algorithm"),
                grade = Grade.F
            )
            .withRegistration(
                lecture = aLecture(term = "2020-1", subject = "algorithm"),
                grade = Grade.A_ZERO
            )

        val constraint = RetakingRegisterConstraint(maxGrade = Grade.C_PLUS)
        val result = constraint.comply(student, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

}