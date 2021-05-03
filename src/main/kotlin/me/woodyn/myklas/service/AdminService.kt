package me.woodyn.myklas.service

import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import me.woodyn.myklas.persistence.repository.LectureRepository
import me.woodyn.myklas.persistence.repository.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val studentRepository: StudentRepository,
    private val lectureRepository: LectureRepository,
    private val registrationRepository: LectureRegistrationRepository
) {

    @Transactional
    fun clear() {
        registrationRepository.deleteAll()
        studentRepository.deleteAll()
        lectureRepository.deleteAll()
    }

    @Transactional
    fun clearTerm(term: String) {
        registrationRepository.deleteAllByLectureTerm(term)
        for (lecture in lectureRepository.findAllByTerm(term)) {
            lecture.numAvailable = lecture.capacity
        }
    }

}