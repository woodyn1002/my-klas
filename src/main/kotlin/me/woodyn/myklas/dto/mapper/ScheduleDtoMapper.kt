package me.woodyn.myklas.dto.mapper

import me.woodyn.myklas.dto.ScheduleDto
import me.woodyn.myklas.persistence.model.Schedule
import org.springframework.stereotype.Component

@Component
class ScheduleDtoMapper {

    fun mapToDto(schedule: Schedule): ScheduleDto.Result =
        ScheduleDto.Result(
            dayOfWeek = schedule.dayOfWeek,
            beginTime = schedule.beginTime,
            endTime = schedule.endTime
        )

}