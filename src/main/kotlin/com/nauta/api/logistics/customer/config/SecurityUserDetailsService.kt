package com.nauta.api.logistics.customer.config

import com.nauta.api.logistics.customer.model.mapper.CustomerMapper
import com.nauta.api.logistics.customer.repository.CustomerRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailsService(
    private val customerRepository: CustomerRepository,
    private val customerMapper: CustomerMapper
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return customerRepository.findByEmail(username)
            ?.let { customer -> customerMapper.toAuthenticatedUser(customer) }
            ?: throw UsernameNotFoundException("User details not found for the user: $username")
    }
}