package com.nauta.api.logistics.customer.exceptionhandling.exception

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError

class RegisterCustomerDataException(
    errorMessage: String = "Internal error registering customer logistic data. ",
    apiError: ApiError
) : MicroserviceException(errorMessage, apiError) {
}