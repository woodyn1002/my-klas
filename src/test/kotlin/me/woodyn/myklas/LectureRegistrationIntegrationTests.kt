package me.woodyn.myklas

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.inspectors.forAny
import io.kotest.matchers.shouldBe
import me.woodyn.myklas.helper.*
import me.woodyn.myklas.persistence.repository.LectureRegistrationRepository
import me.woodyn.myklas.persistence.repository.LectureRepository
import me.woodyn.myklas.persistence.repository.StudentRepository
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Rollback
class LectureRegistrationIntegrationTests(
    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val studentRepository: StudentRepository,

    @Autowired
    private val lectureRepository: LectureRepository,

    @Autowired
    private val lectureRegistrationRepository: LectureRegistrationRepository,

    @Autowired
    private val objectMapper: ObjectMapper
) {

    @Test
    fun `List registrations`() {
        val student = studentRepository.save(
            aStudent(id = null, studentNumber = "2021001001")
        )
        val (lectureA, lectureB) = lectureRepository.saveAll(
            listOf(
                aLecture(id = null, lectureNumber = "0000-1-1077-01"),
                aLecture(id = null, lectureNumber = "0000-1-1077-02")
            )
        )

        lectureRegistrationRepository.saveAll(
            listOf(
                aLectureRegistration(id = null, student = student, lecture = lectureA),
                aLectureRegistration(id = null, student = student, lecture = lectureB)
            )
        )

        mockMvc.get("/students/{studentId}/registrations", student.id) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Collection<*>>(2))
            jsonPath(
                "$.[*].student.studentNumber",
                everyItem(equalTo("2021001001"))
            )
            jsonPath(
                "$.[*].lecture.lectureNumber",
                containsInAnyOrder(
                    "0000-1-1077-01", "0000-1-1077-02"
                )
            )
        }
    }

    @Test
    fun `Register a lecture`() {
        val student = studentRepository.save(
            aStudent(id = null, studentNumber = "2021001001")
        )
        val lecture = lectureRepository.save(
            aLecture(id = null, lectureNumber = "0000-1-1077-01")
        )

        val dto = aLectureRegistrationRegisterDto(lectureId = lecture.id!!)

        mockMvc.post("/students/{studentId}/register", student.id) {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
        }

        lectureRegistrationRepository.findAll().forAny {
            it.student.id shouldBe student.id
            it.lecture.id shouldBe lecture.id
        }
    }

    @Test
    fun `Respond Bad Request when registering a lecture which invalidates the timetable`() {
        val student = studentRepository.save(
            aStudent(id = null, studentNumber = "2021001001")
        )
        val (oldLecture, newLecture) = lectureRepository.saveAll(
            listOf(
                aLecture(id = null, lectureNumber = "0000-1-1077-01")
                    .withSchedule(
                        id = null,
                        dayOfWeek = DayOfWeek.MONDAY,
                        beginTime = LocalTime.of(12, 0),
                        endTime = LocalTime.of(13, 0)
                    ),
                aLecture(id = null, lectureNumber = "0000-1-1077-02")
                    .withSchedule(
                        id = null,
                        dayOfWeek = DayOfWeek.MONDAY,
                        beginTime = LocalTime.of(12, 30),
                        endTime = LocalTime.of(13, 30)
                    ),
            )
        )
        lectureRegistrationRepository.save(
            aLectureRegistration(id = null, student = student, lecture = oldLecture)
        )

        val dto = aLectureRegistrationRegisterDto(lectureId = newLecture.id!!)

        mockMvc.post("/students/{studentId}/register", student.id) {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isBadRequest() }
        }
    }

}