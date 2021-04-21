package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("basic")
class NoSameLevelLiberalArtRegisterConstraint : RegisterConstraint {

    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = student.getRegistrations(lecture.term)

        return registrations.none {
            it.lecture.liberalArtArea == lecture.liberalArtArea
                    && it.lecture.level == lecture.level
        }
    }

}