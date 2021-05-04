package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureRegistration
import org.junit.jupiter.api.Test

internal class MaxCreditRegisterCheckerTest {

    @Test
    fun `Comply when not exceeding the credit limit`() {
        val registrations = listOf(
            aLectureRegistration(lecture = aLecture(credit = 3)),
            aLectureRegistration(lecture = aLecture(credit = 3))
        )

        val checker = MaxCreditRegisterChecker(maxCredit = 10)
        val result = checker.check(registrations, aLecture(credit = 3))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when exceeding the credit limit`() {
        val registrations = listOf(
            aLectureRegistration(lecture = aLecture(credit = 3)),
            aLectureRegistration(lecture = aLecture(credit = 3))
        )

        val checker = MaxCreditRegisterChecker(maxCredit = 8)
        val result = checker.check(registrations, aLecture(credit = 3))

        result.shouldBeFalse()
    }

}