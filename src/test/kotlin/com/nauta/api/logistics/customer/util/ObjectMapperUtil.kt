package com.nauta.api.logistics.customer.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ObjectMapperUtil(
    val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

    val resourceBasePath = "/mock/data/"

    inline fun <reified T> readJsonFromTestResources(filePath: String): T {
        val resource = javaClass.getResource("$resourceBasePath$filePath")
            ?: throw IllegalArgumentException("File not found: $filePath")

        return objectMapper.readValue(resource, T::class.java)
    }

}