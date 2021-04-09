package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("basic")
class NoSameLevelLiberalArtRegisterConstraint(
    private val registrationRepository: LectureRegistrationRepository
) : RegisterConstraint {

    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = registrationRepository.findRegistrationsBelongToTerm(
            student, lecture.term
        )

        return registrations.none {
            it.lecture.liberalArtArea == lecture.liberalArtArea
                    && it.lecture.level == lecture.level
        }
    }

}