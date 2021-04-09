package me.woodyn.myklas.presentation

import me.woodyn.myklas.dto.LectureDto
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.service.LectureService
import me.woodyn.myklas.service.spec.LectureSpecs
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.*

@RestController
class LectureController(
    private val lectureService: LectureService
) {

    @GetMapping("/lectures")
    fun searchLectures(@RequestParam(required = false) term: String?): List<LectureDto.Result> {
        var spec: Specification<Lecture>? = null
        if (term != null) {
            spec = LectureSpecs.term(term)
        }
        return lectureService.search(spec)
    }

    @PostMapping("/lectures")
    fun postLecture(@RequestBody dto: LectureDto.Create): LectureDto.Result =
        lectureService.create(dto)

}