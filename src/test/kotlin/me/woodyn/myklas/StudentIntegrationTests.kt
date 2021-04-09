package me.woodyn.myklas

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.inspectors.forAny
import io.kotest.matchers.shouldBe
import me.woodyn.myklas.helper.aStudent
import me.woodyn.myklas.helper.aStudentCreateDto
import me.woodyn.myklas.persistence.repository.StudentRepository
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.hasSize
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
class StudentIntegrationTests(
    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    private val studentRepository: StudentRepository,

    @Autowired
    private val objectMapper: ObjectMapper
) {

    @Test
    fun `List students`() {
        studentRepository.saveAll(
            listOf(
                aStudent(id = null, studentNumber = "2021001001"),
                aStudent(id = null, studentNumber = "2021001002")
            )
        )

        mockMvc.get("/students") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$", hasSize<Collection<*>>(2))
            jsonPath(
                "$.[*].studentNumber", containsInAnyOrder(
                    "2021001001", "2021001002"
                )
            )
        }
    }

    @Test
    fun `Post a student`() {
        val dto = aStudentCreateDto(studentNumber = "2021001001")

        mockMvc.post("/students") {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { exists() }
        }

        studentRepository.findAll().forAny {
            it.studentNumber shouldBe "2021001001"
        }
    }

}