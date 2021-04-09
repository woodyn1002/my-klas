package me.woodyn.myklas.helper

import me.woodyn.myklas.dto.LectureRegistrationDto
import me.woodyn.myklas.persistence.model.Grade
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.model.Student

fun aLectureRegistration(
    id: Long? = 1,
    student: Student = aStudent(),
    lecture: Lecture = aLecture(),
    grade: Grade? = null
): LectureRegistration = LectureRegistration(
    id = id,
    student = student,
    lecture = lecture,
    grade = grade
)

fun aLectureRegistrationRegisterDto(
    lectureId: Long = 1
): LectureRegistrationDto.Register = LectureRegistrationDto.Register(
    lectureId = lectureId
)
