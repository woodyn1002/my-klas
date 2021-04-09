package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Schedule
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Qualifier("basic")
class ValidTimetableRegisterConstraint(
    private val registrationRepository: LectureRegistrationRepository,
) : RegisterConstraint {

    @Transactional(readOnly = true)
    override fun comply(student: Student, lecture: Lecture): Boolean {
        val registrations = registrationRepository.findRegistrationsBelongToTerm(
            student, lecture.term
        )

        val schedules = registrations.flatMap { it.lecture.schedules }
        return isValid(lecture.schedules, schedules)
    }

    fun isValid(newSchedules: Collection<Schedule>, oldSchedules: Collection<Schedule>): Boolean {
        for (a in newSchedules) {
            for (b in oldSchedules) {
                if (a == b) continue
                if (a.dayOfWeek != b.dayOfWeek) continue

                val noConflict = (b.beginTime >= a.endTime || a.beginTime >= b.endTime)
                if (!noConflict) {
                    return false
                }
            }
        }
        return true
    }

}