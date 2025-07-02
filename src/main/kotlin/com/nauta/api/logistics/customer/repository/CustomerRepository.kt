package com.nauta.api.logistics.customer.repository

import com.nauta.api.logistics.customer.model.entity.CustomerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<CustomerEntity, Long> {
    fun findByEmail(email: String): CustomerEntity?
}