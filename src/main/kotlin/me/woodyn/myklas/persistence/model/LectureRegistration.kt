package me.woodyn.myklas.persistence.model

import javax.persistence.*

@Entity
class LectureRegistration(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val student: Student,

    @ManyToOne
    val lecture: Lecture,

    @Enumerated(EnumType.STRING)
    var grade: Grade? = null
)