package com.fiverr.springoverviewdemo.repository

import com.fiverr.springoverviewdemo.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>
