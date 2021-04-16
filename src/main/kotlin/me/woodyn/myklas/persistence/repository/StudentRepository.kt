package me.woodyn.myklas.persistence.repository

import me.woodyn.myklas.persistence.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType

@Repository
interface StudentRepository : JpaRepository<Student, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select std from Student std where std.id = ?1")
    fun findByIdForUpdate(id: Long): Optional<Student>

}