package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.springframework.stereotype.Component

@Component
class NoSameLevelLiberalArtChecker : SameTermConstraint.Checker {

    override fun check(registrations: Collection<LectureRegistration>, lecture: Lecture): Boolean {
        return registrations.none {
            it.lecture.liberalArtArea == lecture.liberalArtArea
                    && it.lecture.level == lecture.level
        }
    }

}