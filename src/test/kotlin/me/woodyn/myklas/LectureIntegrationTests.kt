package me.woodyn.myklas

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.inspectors.forAny
import io.kotest.matchers.shouldBe
import me.woodyn.myklas.helper.aLecture
import me.woodyn.myklas.helper.aLectureCreateDto
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.aStudentCreateDto
import me.woodyn.myklas.persistence.repository.LectureRepository
import org.hamcrest.Matchers
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Rollback
class LectureIntegrationTests(
    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val lectureRepository: LectureRepository,

    @Autowired
    private val objectMapper: ObjectMapper
) {

    @Test
    fun `List lectures`() {
        lectureRepository.saveAll(
            listOf(
                aLecture(id = null, lectureNumber = "0000-1-1077-01"),
                aLecture(id = null, lectureNumber = "0000-1-1077-02")
            )
        )

        mockMvc.get("/lectures") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$", Matchers.hasSize<Collection<*>>(2))
            jsonPath(
                "$.[*].lectureNumber", Matchers.containsInAnyOrder(
                    "0000-1-1077-01", "0000-1-1077-02"
                )
            )
        }
    }

    @Test
    fun `Post a lecture`() {
        val dto = aLectureCreateDto(lectureNumber = "0000-1-1077-01")

        mockMvc.post("/lectures") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { exists() }
        }

        lectureRepository.findAll().forAny {
            it.lectureNumber shouldBe "0000-1-1077-01"
        }
    }

}