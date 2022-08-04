package com.fiverr.springoverviewdemo.web

import com.fiverr.springoverviewdemo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class UserController(private val service: UserService) {
    @GetMapping("/users")
    fun findAll() = service.findAll()

    @PostMapping("/users")
    fun create(@RequestBody request: UserCreateRequest) = service.create(request.name, request.birthdate)

    @GetMapping("/users/{id}")
    fun findById(@PathVariable id: String) = service.findById(id)

    @PutMapping("/users/{id}")
    fun update(@PathVariable id: String, @RequestBody request: UserUpdateRequest) = service.update(id, request.name)
}

data class UserUpdateRequest(
    val name: String,
)

data class UserCreateRequest(
    val name: String,
    val birthdate: Instant = Instant.now(),
)
