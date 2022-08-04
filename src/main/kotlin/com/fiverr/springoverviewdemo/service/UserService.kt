package com.fiverr.springoverviewdemo.service

import com.fiverr.springoverviewdemo.kafka.UserCreated
import com.fiverr.springoverviewdemo.kafka.UserUpdated
import com.fiverr.springoverviewdemo.model.User
import com.fiverr.springoverviewdemo.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserService(private val repository: UserRepository, private val kafka: KafkaTemplate<String, Any>) {
    fun findAll() = repository.findAll()
    fun findById(id: String) = repository.findByIdOrNull(id) ?: error("user not found")

    fun create(name: String, birthdate: Instant): User {
        val user = repository.save(User(name = name, birthdate = birthdate))
        kafka.send("demo-user", user.id, UserCreated(user.id, user.name, user.birthdate))
        return user
    }

    fun update(id: String, name: String): User {
        val user = repository.findByIdOrNull(id) ?: error("user not found")
        val updated = repository.save(user.copy(name = name))
        kafka.send("demo-user", user.id, UserUpdated(user.id, before = UserUpdated.UserDetails(user.name), after = UserUpdated.UserDetails(updated.name)))
        return updated
    }
}
