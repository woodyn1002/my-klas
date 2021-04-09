package me.woodyn.myklas.presentation

import me.woodyn.myklas.dto.StudentDto
import me.woodyn.myklas.service.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController(
    private val studentService: StudentService
) {

    @GetMapping("/students")
    fun listStudents(): List<StudentDto.Result> =
        studentService.list()

    @PostMapping("/students")
    fun postStudent(@RequestBody dto: StudentDto.Create): StudentDto.Result =
        studentService.create(dto)

}