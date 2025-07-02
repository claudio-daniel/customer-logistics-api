package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.model.api.request.LoginRequestDTO
import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.api.response.LoginResponseDTO
import com.nauta.api.logistics.customer.service.impl.CustomerServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val customerServiceImpl: CustomerServiceImpl
) {

    @Operation(summary = "Customer registration with logistic data access")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Customer Created"),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @PostMapping("/register")
    fun registerUser(@RequestBody customer: RegisterCustomerRequest): ResponseEntity<String> {
        return customerServiceImpl.registerCustomer(customer)
    }

    @Operation(summary = "Generate JWT for an existing customer using username and password")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Customer Created"),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @PostMapping("/auth/login")
    fun apiLogin(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        return customerServiceImpl.apiLogin(loginRequest)
    }
}