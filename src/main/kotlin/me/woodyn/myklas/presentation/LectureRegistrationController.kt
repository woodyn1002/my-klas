package me.woodyn.myklas.presentation

import me.woodyn.myklas.dto.LectureRegistrationDto
import me.woodyn.myklas.service.LectureRegistrationService
import org.springframework.web.bind.annotation.*

@RestController
class LectureRegistrationController(
    private val lectureRegistrationService: LectureRegistrationService
) {

    @GetMapping("/students/{studentId}/registrations")
    fun listRegistrations(@PathVariable studentId: Long): List<LectureRegistrationDto.Result> =
        lectureRegistrationService.list()

    @PostMapping("/students/{studentId}/register")
    fun register(
        @PathVariable studentId: Long,
        @RequestBody dto: LectureRegistrationDto.Register
    ): LectureRegistrationDto.Result =
        lectureRegistrationService.register(studentId, dto)

}