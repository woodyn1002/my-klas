package me.woodyn.myklas.persistence.repository

import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LectureRegistrationRepository : JpaRepository<LectureRegistration, Long> {

    @Query(
        "select reg from LectureRegistration reg inner join reg.lecture lec " +
                "where reg.student = ?1 and lec.term = ?2"
    )
    fun findRegistrationsBelongToTerm(
        student: Student,
        lectureTerm: String
    ): Set<LectureRegistration>

    @Query(
        "select reg from LectureRegistration reg inner join reg.lecture lec " +
                "where reg.student = ?1 and lec.subject = ?2 and lec.term <> ?3"
    )
    fun findOldRegistrationsBySubject(
        student: Student,
        lectureSubject: String,
        term: String
    ): Set<LectureRegistration>

}