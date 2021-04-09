package me.woodyn.myklas.dto

import me.woodyn.myklas.persistence.model.Grade

interface LectureRegistrationDto {

    data class Register(
        val lectureId: Long
    )

    data class Result(
        val student: StudentDto.Result,
        val lecture: LectureDto.Result,
        val grade: Grade?
    )

}