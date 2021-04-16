package me.woodyn.myklas.persistence.repository

import me.woodyn.myklas.persistence.model.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType

@Repository
interface LectureRepository : JpaRepository<Lecture, Long>, JpaSpecificationExecutor<Lecture> {

    fun findAllByTerm(term: String): List<Lecture>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select lec from Lecture lec where lec.id = ?1")
    fun findByIdForUpdate(id: Long): Optional<Lecture>

}