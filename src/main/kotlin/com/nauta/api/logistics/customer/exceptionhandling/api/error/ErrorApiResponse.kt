package com.nauta.api.logistics.customer.exceptionhandling.api.error

import java.time.ZonedDateTime

class ErrorApiResponse(
    val message: String,
    val apiError: ApiError,
    val timestamp: ZonedDateTime
)