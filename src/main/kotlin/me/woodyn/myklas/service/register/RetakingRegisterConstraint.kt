package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Qualifier("basic")
class RetakingRegisterConstraint(
    @Value("\${domain.constraints.max-grade-for-retaking}")
    private val maxGrade: Grade
) : RegisterConstraint {

    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = student.getCompletedRegistrations(
            subject = lecture.subject,
            termBefore = lecture.term
        )

        if (registrations.isEmpty()) return true

        return registrations.size == 1 && registrations.first().grade?.let { it <= maxGrade } ?: false
    }

}