package me.woodyn.myklas.persistence.model

import javax.persistence.*

@Entity
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val studentNumber: String
) {
    @OneToMany(mappedBy = "student", cascade = [CascadeType.REMOVE])
    val lectureRegistrations: MutableSet<LectureRegistration> = mutableSetOf()
}