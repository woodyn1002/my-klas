package me.woodyn.myklas.helper

import me.woodyn.myklas.dto.StudentDto
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