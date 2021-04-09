package me.woodyn.myklas.dto.mapper

import me.woodyn.myklas.dto.LectureRegistrationDto
import me.woodyn.myklas.persistence.model.LectureRegistration
import org.springframework.stereotype.Component

@Component
class LectureRegistrationDtoMapper(
    private val studentDtoMapper: StudentDtoMapper,
    private val lectureDtoMapper: LectureDtoMapper
) {

    fun mapToDto(lectureRegistration: LectureRegistration): LectureRegistrationDto.Result =
        LectureRegistrationDto.Result(
            student = studentDtoMapper.mapToDto(lectureRegistration.student),
            lecture = lectureDtoMapper.mapToDto(lectureRegistration.lecture),
            grade = lectureRegistration.grade
        )

}