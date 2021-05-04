package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureRegistration
import me.woodyn.myklas.persistence.model.LiberalArtArea
import org.junit.jupiter.api.Test

internal class NoSameLevelLiberalArtCheckerTest {

    @Test
    fun `Comply when no lectures conflict`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(
                    term = "2021-1",
                    level = 1,
                    liberalArtArea = LiberalArtArea.PHILOSOPHY
                )
            )
        )

        val checker = NoSameLevelLiberalArtChecker()
        val result = checker.check(
            registrations,
            aLecture(
                term = "2021-1",
                level = 1,
                liberalArtArea = LiberalArtArea.IT
            )
        )

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when some lectures conflict each other`() {
        val registrations = listOf(
            aLectureRegistration(
                lecture = aLecture(
                    term = "2021-1",
                    level = 1,
                    liberalArtArea = LiberalArtArea.IT
                )
            )
        )

        val checker = NoSameLevelLiberalArtChecker()
        val result = checker.check(
            registrations,
            aLecture(
                term = "2021-1",
                level = 1,
                liberalArtArea = LiberalArtArea.IT
            )
        )

        result.shouldBeFalse()
    }

}