package com.fiverr.springoverviewdemo.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@KafkaListener(topics = ["demo-user"], groupId = "spring-overview-demo")
class Handlers {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaHandler
    fun handle(event: UserCreated) {
        log.info("User created with name: ${event.name}")
    }

    @KafkaHandler
    fun handle(event: UserUpdated) {
        log.info("User changed name from: ${event.before.name} to ${event.after.name}")
    }
}

data class UserCreated(
    val id: String,
    val name: String,
    val birthdate: Instant,
)

data class UserUpdated(
    val id: String,
    val before: UserDetails,
    val after: UserDetails,
) {
    data class UserDetails(
        val name: String,
    )
}
