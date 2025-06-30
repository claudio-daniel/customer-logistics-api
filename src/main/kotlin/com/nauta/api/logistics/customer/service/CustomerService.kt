package com.nauta.api.logistics.customer.service

import com.nauta.api.logistics.customer.model.api.request.LoginRequestDTO
import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.api.response.LoginResponseDTO
import org.springframework.http.ResponseEntity

interface CustomerService {
    fun apiLogin(loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO>

    fun registerCustomer(registerCustomerRequest: RegisterCustomerRequest): ResponseEntity<String>
}