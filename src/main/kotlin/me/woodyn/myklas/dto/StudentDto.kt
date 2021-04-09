package me.woodyn.myklas.dto

interface StudentDto {

    data class Create(
        val studentNumber: String
    )

    data class Result(
        val id: Long,
        val studentNumber: String
    )

}