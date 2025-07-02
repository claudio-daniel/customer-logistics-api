package com.nauta.api.logistics.customer.mock

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError
import com.nauta.api.logistics.customer.exceptionhandling.exception.RegisterCustomerDataException
import java.sql.SQLException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.security.authentication.BadCredentialsException

fun mockRegisterCustomerLogisticDataException(): RegisterCustomerDataException = RegisterCustomerDataException(
    apiError = ApiError(
        message = "Error to validate data",
        code = "ERR-003"
    )
)

fun mockHibernateConstraintViolation(): ConstraintViolationException =
    ConstraintViolationException("Unique constraint failed", SQLException(), "booking_code_unique")

fun mockBadCredentialsException(): BadCredentialsException = BadCredentialsException("Invalid credentials.")