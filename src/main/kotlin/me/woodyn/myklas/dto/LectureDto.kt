package me.woodyn.myklas.dto

import me.woodyn.myklas.persistence.model.LiberalArtArea

interface LectureDto {
    
    data class Create(
        val lectureNumber: String,
        val term: String,
        val name: String,
        val subject: String,
        val level: Int,
        val liberalArtArea: LiberalArtArea?,
        val credit: Int,
        val schedules: List<ScheduleDto.Create>,
        val capacity: Int
    )

    data class Result(
        val id: Long,
        val lectureNumber: String,
        val term: String,
        val name: String,
        val subject: String,
        val level: Int,
        val liberalArtArea: LiberalArtArea?,
        val credit: Int,
        val schedules: List<ScheduleDto.Result>,
        val capacity: Int
    )
    
}