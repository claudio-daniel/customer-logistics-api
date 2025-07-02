package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.entity.CustomerEntity
import org.springframework.security.core.userdetails.User
import java.sql.Date

fun toAuthenticatedUser(customer: CustomerEntity): User = User(
    customer.email,
    customer.pwd,
    customer.authorities.toSimpleGrantedAuthorities()
)

fun toCustomerEntity(registerCustomerRequest: RegisterCustomerRequest, encryptedPassword: String): CustomerEntity =
    CustomerEntity(
        id = null,
        name = registerCustomerRequest.name,
        email = registerCustomerRequest.email,
        mobileNumber = registerCustomerRequest.mobileNumber,
        pwd = encryptedPassword,
        role = registerCustomerRequest.role,
        createdDate = Date(System.currentTimeMillis()),
        authorities = toAuthorityEntity(registerCustomerRequest)
    )