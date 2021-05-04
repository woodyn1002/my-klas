package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MaxCreditRegisterChecker(
    @Value("\${domain.constraints.max-credit}")
    private val maxCredit: Int
) : SameTermConstraint.Checker {

    override fun check(registrations: Collection<LectureRegistration>, lecture: Lecture): Boolean {
        val sumCredit = registrations.sumOf { it.lecture.credit } + lecture.credit
        return sumCredit <= maxCredit
    }

}