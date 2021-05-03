package me.woodyn.myklas.persistence.repository

import me.woodyn.myklas.persistence.model.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository : JpaRepository<Lecture, Long>, JpaSpecificationExecutor<Lecture>