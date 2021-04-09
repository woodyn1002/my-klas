package me.woodyn.myklas.service.register

import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.Student

interface RegisterConstraint {

    fun comply(student: Student, lecture: Lecture): Boolean

}