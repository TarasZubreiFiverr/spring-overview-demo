package com.fiverr.springoverviewdemo.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.Instant

data class User(
    @MongoId(FieldType.OBJECT_ID)
    val id: String = ObjectId().toString(),
    val name: String,
    val birthdate: Instant,
)
