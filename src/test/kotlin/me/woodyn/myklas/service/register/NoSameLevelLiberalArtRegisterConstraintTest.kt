package me.woodyn.myklas.service.register

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.withRegistration
import me.woodyn.myklas.persistence.model.LiberalArtArea
import org.junit.jupiter.api.Test

internal class NoSameLevelLiberalArtRegisterConstraintTest {

    @Test
    fun `Comply when no lectures conflict`() {
        val student = aStudent()
            .withRegistration(
                lecture = aLecture(
                    term = "2021-1",
                    level = 1,
                    liberalArtArea = LiberalArtArea.PHILOSOPHY
                )
            )

        val constraint = NoSameLevelLiberalArtRegisterConstraint()
        val result = constraint.comply(
            student, aLecture(
                term = "2021-1",
                level = 1,
                liberalArtArea = LiberalArtArea.IT
            )
        )

        result.shouldBeTrue()
    }

    @Test
    fun `Doesn't comply when some lectures conflict each other`() {
        val student = aStudent()
            .withRegistration(
                lecture = aLecture(
                    term = "2021-1",
                    level = 1,
                    liberalArtArea = LiberalArtArea.IT
                )
            )

        val constraint = NoSameLevelLiberalArtRegisterConstraint()
        val result = constraint.comply(
            student, aLecture(
                term = "2021-1",
                level = 1,
                liberalArtArea = LiberalArtArea.IT
            )
        )

        result.shouldBeFalse()
    }

}