package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Qualifier("basic")
class RetakingRegisterConstraint(
    private val registrationRepository: LectureRegistrationRepository,

    @Value("\${domain.constraints.max-grade-for-retaking}")
    private val maxGrade: Grade
) : RegisterConstraint {

    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = registrationRepository.findOldRegistrationsBySubject(
            student = student,
            lectureSubject = lecture.subject,
            term = lecture.term
        )

        if (registrations.isEmpty()) return true

        return registrations.size == 1 && registrations.first().grade?.let { it <= maxGrade } ?: false
    }

}