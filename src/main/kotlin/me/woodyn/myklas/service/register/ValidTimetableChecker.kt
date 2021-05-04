package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.model.Schedule
import org.springframework.stereotype.Component

@Component
class ValidTimetableChecker : SameTermConstraint.Checker {

    override fun check(registrations: Collection<LectureRegistration>, lecture: Lecture): Boolean {
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