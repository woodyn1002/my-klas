package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Qualifier("basic")
class MaxCreditRegisterConstraint(
    private val registrationRepository: LectureRegistrationRepository,

    @Value("\${domain.constraints.max-credit}")
    private val maxCredit: Int
) : RegisterConstraint {

    @Transactional(readOnly = true)
    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = registrationRepository.findRegistrationsBelongToTerm(
            student, lecture.term
        )

        val sumCredit = registrations.sumOf { it.lecture.credit } + lecture.credit
        return sumCredit <= maxCredit
    }

}