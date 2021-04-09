package me.woodyn.myklas.dto

import java.time.DayOfWeek
import java.time.LocalTime

interface ScheduleDto {
    
    data class Create(
        val dayOfWeek: DayOfWeek,
        val beginTime: LocalTime,
        val endTime: LocalTime
    )

    data class Result(
        val dayOfWeek: DayOfWeek,
        val beginTime: LocalTime,
        val endTime: LocalTime
    )
    
}