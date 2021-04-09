package me.woodyn.myklas.persistence.model

import javax.persistence.*

@Entity
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val lectureNumber: String,
    val term: String,
    val name: String,
    val subject: String,
    val level: Int,

    @Enumerated(EnumType.STRING)
    val liberalArtArea: LiberalArtArea? = null,

    val credit: Int,
    var capacity: Int,
    var numAvailable: Int
) {
    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL])
    val schedules: MutableSet<Schedule> = mutableSetOf()

    @OneToMany(mappedBy = "lecture", cascade = [CascadeType.REMOVE])
    val registrations: MutableSet<LectureRegistration> = mutableSetOf()
}
