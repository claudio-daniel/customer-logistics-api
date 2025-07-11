package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.filter.JWTTokenGeneratorFilter
import com.nauta.api.logistics.customer.model.api.dto.CustomerDTO
import com.nauta.api.logistics.customer.model.api.request.LoginRequestDTO
import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.api.response.LoginResponseDTO
import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import com.nauta.api.logistics.customer.model.mapper.toCustomerEntity
import com.nauta.api.logistics.customer.repository.CustomerRepository
import com.nauta.api.logistics.customer.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenGeneratorFilter: JWTTokenGeneratorFilter,
) : CustomerService {

    override fun apiLogin(loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {

        val authenticationRequest =
            UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username, loginRequest.password)

        return authenticationManager
            .authenticate(authenticationRequest)
            ?.takeIf { it.isAuthenticated }
            ?.let { jwtTokenGeneratorFilter.generateJWT(it) }
            ?.let { generatedJWT ->
                ResponseEntity.status(HttpStatus.OK)
                    .header(SecurityConstants.JWT_HEADER, generatedJWT)
                    .body(LoginResponseDTO(HttpStatus.OK.reasonPhrase, generatedJWT))
            }
            ?: throw BadCredentialsException("Invalid credentials.")
    }

    override fun findAuthorizedCustomer(): CustomerDTO {
        val username = SecurityContextHolder.getContext().authentication?.name.orEmpty()
        val customerId = customerRepository.findByEmail(username)?.id

        return CustomerDTO(
            username = username,
            customerId = customerId!!
        )
    }

    override fun registerCustomer(registerCustomerRequest: RegisterCustomerRequest): ResponseEntity<String> {
        try {
            return registerCustomerRequest
                .let { customerRequest -> passwordEncoder.encode(customerRequest.pwd) to customerRequest }
                .let { (encryptedPassword, customerRequest) ->
                    toCustomerEntity(customerRequest, encryptedPassword)
                        .loadAssociations()
                }
                .let { customerToSave -> customerRepository.save(customerToSave) }
                .takeIf { savedCustomer -> savedCustomer.verifyId() }
                ?.let {
                    ResponseEntity.status(HttpStatus.CREATED)
                        .body("Customer registered successfully")
                }
                ?: ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Customer registration failed")

        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error when saving customer : " + ex.message)
        }
    }
}