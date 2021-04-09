package me.woodyn.myklas.service.spec

import me.woodyn.myklas.persistence.model.Lecture
import org.springframework.data.jpa.domain.Specification

object LectureSpecs {

    fun term(term: String): Specification<Lecture> =
        Specification { root, _, builder ->
            builder.equal(root.get<String>("term"), term)
        }

}