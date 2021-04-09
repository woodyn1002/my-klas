package me.woodyn.myklas.dto.mapper

import me.woodyn.myklas.dto.LectureDto
import me.woodyn.myklas.persistence.model.Lecture
import org.springframework.stereotype.Component

@Component
class LectureDtoMapper(
    private val scheduleDtoMapper: ScheduleDtoMapper
) {

    fun mapToDto(lecture: Lecture): LectureDto.Result =
        LectureDto.Result(
            id = lecture.id!!,
            lectureNumber = lecture.lectureNumber,
            term = lecture.term,
            name = lecture.name,
            subject = lecture.subject,
            level = lecture.level,
            liberalArtArea = lecture.liberalArtArea,
            credit = lecture.credit,
            schedules = lecture.schedules.map(scheduleDtoMapper::mapToDto),
            capacity = lecture.capacity
        )

}