package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Qualifier("basic")
class RetakingConstraint(
    private val registrationRepository: LectureRegistrationRepository,

    @Autowired(required = false)
    private val checkers: List<Checker> = emptyList()
) : RegisterConstraint {

    @Transactional(readOnly = true)
    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = registrationRepository.findOldRegistrationsBySubject(
            student = student,
            subject = lecture.subject,
            term = lecture.term
        )
        return checkers.all { it.check(registrations, lecture) }
    }

    interface Checker {

        fun check(
            sameSubjectRegistrations: Collection<LectureRegistration>,
            lecture: Lecture
        ): Boolean

    }

}