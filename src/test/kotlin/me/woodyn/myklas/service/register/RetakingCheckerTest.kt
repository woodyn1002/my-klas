package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureRegistration
import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.junit.jupiter.api.Test

internal class RetakingCheckerTest {

    @Test
    fun `Comply when the lecture hasn't been taken ever`() {
        val registrations = emptyList<LectureRegistration>()

        val checker = RetakingChecker(maxGrade = Grade.C_PLUS)
        val result = checker.check(registrations, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when the lecture has been taken with the grade which exceeds the limit`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(term = "2019-2", subject = "algorithm"),
                grade = Grade.B_ZERO
            )
        )

        val checker = RetakingChecker(maxGrade = Grade.C_PLUS)
        val result = checker.check(registrations, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

    @Test
    fun `Doesn't comply when the lecture has been retaken already`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(term = "2019-2", subject = "algorithm"),
                grade = Grade.F
            ),
            aLectureRegistration(
                lecture = aLecture(term = "2020-1", subject = "algorithm"),
                grade = Grade.A_ZERO
            )
        )

        val checker = RetakingChecker(maxGrade = Grade.C_PLUS)
        val result = checker.check(registrations, aLecture(term = "2021-1", subject = "algorithm"))

        result.shouldBeFalse()
    }

}