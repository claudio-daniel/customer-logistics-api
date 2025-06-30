package com.nauta.api.logistics.customer.model.enums

import org.apache.coyote.BadRequestException

enum class AuthoritiesDB(val authorityName: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    companion object {
        fun fromString(value: String): AuthoritiesDB {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw BadRequestException("Invalid customer authorities.")
        }
    }
}