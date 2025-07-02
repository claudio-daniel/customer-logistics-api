package com.nauta.api.logistics.customer.repository

import com.nauta.api.logistics.customer.model.entity.ContainerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContainerRepository : JpaRepository<ContainerEntity, Long> {

    fun findByBookingCustomerId(customerId: Long): List<ContainerEntity>

    fun findByIdAndBookingCustomerId(containerId: Long, customerId: Long): ContainerEntity?
}