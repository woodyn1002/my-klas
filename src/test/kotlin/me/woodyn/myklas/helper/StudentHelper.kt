package me.woodyn.myklas.helper

import me.woodyn.myklas.dto.StudentDto
import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.model.Student

fun aStudent(
    id: Long? = 1,
    studentNumber: String = "2021001001"
): Student = Student(
    id = id,
    studentNumber = studentNumber
)

fun aStudentCreateDto(
    studentNumber: String = "2021001001"
): StudentDto.Create = StudentDto.Create(
    studentNumber = studentNumber
)

fun Student.withRegistration(
    id: Long? = 1,
    lecture: Lecture = aLecture(),
    grade: Grade? = null
): Student = this.apply {
    this.lectureRegistrations.add(
        LectureRegistration(
            id = id,
            student = this,
            lecture = lecture,
            grade = grade
        )
    )
}

fun Student.withRegistrations(
    registrations: Collection<LectureRegistration>
): Student = this.apply {
    this.lectureRegistrations.clear()
    this.lectureRegistrations.addAll(registrations)
}