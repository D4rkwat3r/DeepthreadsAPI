package ru.deepthreads.rest

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.core.MongoTemplate
import ru.deepthreads.rest.database.MongoConfig

@SpringBootApplication
class RestApplication

val mongoTemplate = MongoTemplate(MongoConfig().mongoClient(), "Deepthreads")
val mapper: JsonMapper = jacksonMapperBuilder()
	.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
	.serializationInclusion(JsonInclude.Include.NON_NULL)
	.enable(SerializationFeature.INDENT_OUTPUT)
	.build()

fun main(args: Array<String>) {
	runApplication<RestApplication>(*args)
}
