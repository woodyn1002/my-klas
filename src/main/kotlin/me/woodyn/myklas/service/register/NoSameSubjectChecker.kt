package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.springframework.stereotype.Component

@Component
class NoSameSubjectChecker : SameTermConstraint.Checker {

    override fun check(registrations: Collection<LectureRegistration>, lecture: Lecture): Boolean {
        return registrations.none { it.lecture.subject == lecture.subject }
    }

}