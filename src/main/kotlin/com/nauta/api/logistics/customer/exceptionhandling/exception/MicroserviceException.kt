package com.nauta.api.logistics.customer.exceptionhandling.exception

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError

abstract class MicroserviceException(
    errorMessage: String,
    val apiError: ApiError
) : RuntimeException(errorMessage)