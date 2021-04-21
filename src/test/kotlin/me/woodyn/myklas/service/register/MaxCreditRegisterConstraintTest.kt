package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.withRegistration
import org.junit.jupiter.api.Test

internal class MaxCreditRegisterConstraintTest {

    @Test
    fun `Comply when not exceeding the credit limit`() {
        val student = aStudent()
            .withRegistration(lecture = aLecture(credit = 3))
            .withRegistration(lecture = aLecture(credit = 3))

        val constraint = MaxCreditRegisterConstraint(maxCredit = 10)
        val result = constraint.comply(student, aLecture(credit = 3))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when exceeding the credit limit`() {
        val student = aStudent()
            .withRegistration(lecture = aLecture(credit = 3))
            .withRegistration(lecture = aLecture(credit = 3))

        val constraint = MaxCreditRegisterConstraint(maxCredit = 8)
        val result = constraint.comply(student, aLecture(credit = 3))

        result.shouldBeFalse()
    }

}