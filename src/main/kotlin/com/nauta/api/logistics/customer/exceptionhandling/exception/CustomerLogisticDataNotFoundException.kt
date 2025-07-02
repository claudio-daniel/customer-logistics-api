package com.nauta.api.logistics.customer.exceptionhandling.exception

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError

class CustomerLogisticDataNotFoundException(
    errorMessage: String = "Customer logistic data not found. ",
    apiError: ApiError
) : MicroserviceException(errorMessage, apiError)