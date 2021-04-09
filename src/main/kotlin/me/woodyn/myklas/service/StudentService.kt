package me.woodyn.myklas.service

import me.woodyn.myklas.dto.StudentDto
import me.woodyn.myklas.dto.mapper.StudentDtoMapper
import me.woodyn.myklas.persistence.model.Student
import me.woodyn.myklas.persistence.repository.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StudentService(
    private val dtoMapper: StudentDtoMapper,
    private val studentRepository: StudentRepository
) {

    @Transactional
    fun create(dto: StudentDto.Create): StudentDto.Result {
        val student = Student(
            studentNumber = dto.studentNumber
        )
        studentRepository.save(student)

        return dtoMapper.mapToDto(student)
    }

    @Transactional(readOnly = true)
    fun list(): List<StudentDto.Result> =
        studentRepository.findAll().map(dtoMapper::mapToDto)

}