package me.woodyn.myklas.helper

import me.woodyn.myklas.dto.LectureDto
import me.woodyn.myklas.dto.ScheduleDto
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.model.LiberalArtArea
import me.woodyn.myklas.persistence.model.Schedule
import java.time.DayOfWeek
import java.time.LocalTime

fun aLecture(
    id: Long? = 1,
    lectureNumber: String = "0000-1-1077-02",
    term: String = "2021-1",
    name: String = "lecture",
    subject: String = "subject",
    level: Int = 1,
    liberalArtArea: LiberalArtArea? = null,
    credit: Int = 3,
    capacity: Int = 10,
    numAvailable: Int = 10
): Lecture = Lecture(
    id = id,
    lectureNumber = lectureNumber,
    term = term,
    name = name,
    subject = subject,
    level = level,
    liberalArtArea = liberalArtArea,
    credit = credit,
    capacity = capacity,
    numAvailable = numAvailable
)

fun Lecture.withSchedule(
    id: Long? = 1,
    dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    beginTime: LocalTime = LocalTime.of(12, 0),
    endTime: LocalTime = LocalTime.of(13, 0)
): Lecture = this.apply {
    this.schedules.add(Schedule(
        id = id,
        lecture = this,
        dayOfWeek = dayOfWeek,
        beginTime = beginTime,
        endTime = endTime
    ))
}

fun aLectureCreateDto(
    lectureNumber: String = "0000-1-1077-02",
    term: String = "2021-1",
    name: String = "lecture",
    subject: String = "subject",
    level: Int = 1,
    liberalArtArea: LiberalArtArea? = null,
    credit: Int = 3,
    capacity: Int = 10,
    schedules: List<ScheduleDto.Create> = emptyList()
): LectureDto.Create = LectureDto.Create(
    lectureNumber = lectureNumber,
    term = term,
    name = name,
    subject = subject,
    level = level,
    liberalArtArea = liberalArtArea,
    credit = credit,
    capacity = capacity,
    schedules = schedules
)

fun aScheduleCreateDto(
    dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    beginTime: LocalTime = LocalTime.of(12, 0),
    endTime: LocalTime = LocalTime.of(13, 0)
): ScheduleDto.Create = ScheduleDto.Create(
    dayOfWeek = dayOfWeek,
    beginTime = beginTime,
    endTime = endTime
)