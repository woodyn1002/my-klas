package me.woodyn.myklas.dto.mapper

import me.woodyn.myklas.dto.StudentDto
import me.woodyn.myklas.persistence.model.Student
import org.springframework.stereotype.Component

@Component
class StudentDtoMapper {

    fun mapToDto(student: Student): StudentDto.Result =
        StudentDto.Result(
            id = student.id!!,
            studentNumber = student.studentNumber
        )

}