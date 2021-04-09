package me.woodyn.myklas.persistence.repository

import me.woodyn.myklas.persistence.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long>