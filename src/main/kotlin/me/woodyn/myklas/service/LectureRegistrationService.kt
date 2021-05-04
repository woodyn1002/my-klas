package me.woodyn.myklas.service

import me.woodyn.myklas.dto.LectureRegistrationDto
import me.woodyn.myklas.dto.mapper.LectureRegistrationDtoMapper
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import me.woodyn.myklas.persistence.repository.LectureRepository
import me.woodyn.myklas.persistence.repository.StudentRepository
import me.woodyn.myklas.service.register.RegisterConstraint
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class LectureRegistrationService(
    private val dtoMapper: LectureRegistrationDtoMapper,
    private val registrationRepository: LectureRegistrationRepository,
    private val studentRepository: StudentRepository,
    private val lectureRepository: LectureRepository,

    @PersistenceContext
    private val entityManager: EntityManager,

    @Autowired(required = false)
    @Qualifier("basic")
    private val registerConstraints: List<RegisterConstraint> = emptyList()
) {
    private val errorHelper: ServiceErrorHelper = ServiceErrorHelper()

    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun register(
        studentId: Long,
        dto: LectureRegistrationDto.Register
    ): LectureRegistrationDto.Result {
        val student = studentRepository.findByIdForUpdate(studentId).orElseThrow {
            errorHelper.notFound("Student $studentId")
        }
        val lecture = lectureRepository.findById(dto.lectureId).orElseThrow {
            errorHelper.notFound("Lecture ${dto.lectureId}")
        }

        for (constraint in registerConstraints) {
            if (!constraint.comply(student, lecture)) {
                val constraintName = constraint::class.simpleName
                throw errorHelper.badRequest(
                    "Student couldn't register the lecture: violates $constraintName"
                )
            }
        }

        entityManager.detach(lecture)
        val lectureForUpdate = lectureRepository.findByIdForUpdate(dto.lectureId).orElseThrow {
            errorHelper.notFound("Lecture ${dto.lectureId}")
        }

        logger.debug("Lecture ${lectureForUpdate.id} has ${lectureForUpdate.numAvailable} seats available")
        if (lectureForUpdate.numAvailable <= 0) {
            throw errorHelper.badRequest(
                "Student couldn't register the lecture: no available seats"
            )
        }
        lectureForUpdate.numAvailable--
        logger.debug("The remaining seats of the lecture ${lectureForUpdate.id} is ${lectureForUpdate.numAvailable}")

        val registration = LectureRegistration(
            student = student,
            lecture = lectureForUpdate
        )
        registrationRepository.save(registration)

        return dtoMapper.mapToDto(registration)
    }

    @Transactional(readOnly = true)
    fun list(): List<LectureRegistrationDto.Result> =
        registrationRepository.findAll().map(dtoMapper::mapToDto)

}