package me.woodyn.myklas

import io.kotest.matchers.collections.shouldBeEmpty
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureRegistration
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import me.woodyn.myklas.persistence.repository.LectureRepository
import me.woodyn.myklas.persistence.repository.StudentRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Rollback
class AdminIntegrationTests(
    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val studentRepository: StudentRepository,

    @Autowired
    private val lectureRepository: LectureRepository,

    @Autowired
    private val lectureRegistrationRepository: LectureRegistrationRepository
) {

    @Test
    fun `Clear all`() {
        val student = studentRepository.save(
            aStudent(id = null)
        )
        val (lectureA, lectureB) = lectureRepository.saveAll(
            listOf(
                aLecture(id = null),
                aLecture(id = null)
            )
        )
        lectureRegistrationRepository.saveAll(
            listOf(
                aLectureRegistration(id = null, student = student, lecture = lectureA),
                aLectureRegistration(id = null, student = student, lecture = lectureB)
            )
        )

        mockMvc.post("/admin/clear") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

        studentRepository.findAll().shouldBeEmpty()
        lectureRepository.findAll().shouldBeEmpty()
        lectureRegistrationRepository.findAll().shouldBeEmpty()
    }

}