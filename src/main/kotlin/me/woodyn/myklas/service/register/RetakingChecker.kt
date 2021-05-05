package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RetakingChecker(
    @Value("\${domain.constraints.max-grade-for-retaking}")
    private val maxGrade: Grade
) : RetakingConstraint.Checker {

    override fun check(
        sameSubjectRegistrations: Collection<LectureRegistration>,
        lecture: Lecture
    ): Boolean {
        if (sameSubjectRegistrations.isNotEmpty()) {
            if (sameSubjectRegistrations.size > 1) {
                return false
            }

            val old = sameSubjectRegistrations.first()
            if (old.grade == null) {
                return false
            }

            return old.grade!! <= maxGrade
        } else {
            return true
        }
    }

}