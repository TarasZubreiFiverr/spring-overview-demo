package com.fiverr.springoverviewdemo

import com.fiverr.springoverviewdemo.model.User
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SpringOverviewDemoApplicationTests(
    private val mockMvc: MockMvc,
    private val mongo: MongoTemplate,
) {

    @BeforeEach
    @AfterAll
    fun cleanup() {
        mongo.dropCollection<User>()
    }

    @Test
    fun findUserById() {
        val user = mongo.insert(User(name = "Test", birthdate = Instant.now().truncatedTo(ChronoUnit.SECONDS)))

        mockMvc.get("/users/${user.id}").andExpect {
            status { isOk() }
            content { json("""{"id": "${user.id}", "name": "${user.name}", "birthdate": "${user.birthdate}"}""") }
        }
    }
}
