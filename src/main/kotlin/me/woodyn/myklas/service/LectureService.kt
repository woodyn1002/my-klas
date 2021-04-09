package me.woodyn.myklas.service

import me.woodyn.myklas.dto.LectureDto
import me.woodyn.myklas.dto.mapper.LectureDtoMapper
import me.woodyn.myklas.persistence.model.Lecture
import me.woodyn.myklas.persistence.repository.LectureRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureService(
    private val dtoMapper: LectureDtoMapper,
    private val lectureRepository: LectureRepository
) {

    @Transactional
    fun create(dto: LectureDto.Create): LectureDto.Result {
        val lecture = Lecture(
            lectureNumber = dto.lectureNumber,
            term = dto.term,
            name = dto.name,
            subject = dto.subject,
            level = dto.level,
            liberalArtArea = dto.liberalArtArea,
            credit = dto.credit,
            capacity = dto.capacity,
            numAvailable = dto.capacity
        )
        lectureRepository.save(lecture)

        return dtoMapper.mapToDto(lecture)
    }

    @Transactional(readOnly = true)
    fun search(spec: Specification<Lecture>? = null): List<LectureDto.Result> =
        lectureRepository.findAll(spec).map(dtoMapper::mapToDto)

}