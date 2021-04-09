package me.woodyn.myklas.service

import me.woodyn.myklas.dto.LectureRegistrationDto
import me.woodyn.myklas.dto.mapper.LectureRegistrationDtoMapper
import me.woodyn.myklas.persistence.model.LectureRegistration
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import me.woodyn.myklas.persistence.repository.LectureRepository
import me.woodyn.myklas.persistence.repository.StudentRepository
import me.woodyn.myklas.service.register.RegisterConstraint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureRegistrationService(
    private val dtoMapper: LectureRegistrationDtoMapper,
    private val registrationRepository: LectureRegistrationRepository,
    private val studentRepository: StudentRepository,
    private val lectureRepository: LectureRepository,

    @Autowired(required = false)
    @Qualifier("basic")
    private val registerConstraints: List<RegisterConstraint> = emptyList()
) {
    private val errorHelper: ServiceErrorHelper = ServiceErrorHelper()

    @Transactional
    fun register(
        studentId: Long,
        dto: LectureRegistrationDto.Register
    ): LectureRegistrationDto.Result {
        val student = studentRepository.findById(studentId).orElseThrow {
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

        val registration = LectureRegistration(
            student = student,
            lecture = lecture
        )
        registrationRepository.save(registration)

        return dtoMapper.mapToDto(registration)
    }

    @Transactional(readOnly = true)
    fun list(): List<LectureRegistrationDto.Result> =
        registrationRepository.findAll().map(dtoMapper::mapToDto)

}