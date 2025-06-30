package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.entity.AuthorityEntity
import com.nauta.api.logistics.customer.model.entity.CustomerEntity
import com.nauta.api.logistics.customer.model.enums.AuthoritiesDB
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.sql.Date
import java.util.stream.Collectors

@Component
class CustomerMapper {

    fun toAuthenticatedUser(customer: CustomerEntity): User {
        val authorities = customer.authorities!!.stream()
            .map { authority: AuthorityEntity -> SimpleGrantedAuthority(authority.name) }
            .collect(Collectors.toList())

        return User(customer.email, customer.pwd, authorities)
    }

    fun toCustomerEntity(registerCustomerRequest: RegisterCustomerRequest, encryptedPassword: String): CustomerEntity {

        val authority = AuthorityEntity(
            id = null,
            name = AuthoritiesDB.fromString(registerCustomerRequest.role).authorityName,
            customer = null
        )
        return CustomerEntity(
            id = null,
            name = registerCustomerRequest.name,
            email = registerCustomerRequest.email,
            mobileNumber = registerCustomerRequest.mobileNumber,
            pwd = encryptedPassword,
            role = registerCustomerRequest.role,
            createdDate = Date(System.currentTimeMillis()),
            authorities = mutableSetOf(authority)
        )
    }
}