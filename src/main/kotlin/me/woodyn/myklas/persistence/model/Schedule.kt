package me.woodyn.myklas.persistence.model

import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.*

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val lecture: Lecture,

    @Enumerated(EnumType.STRING)
    val dayOfWeek: DayOfWeek,

    val beginTime: LocalTime,
    val endTime: LocalTime
)
