package com.nauta.api.logistics.customer.repository

import com.nauta.api.logistics.customer.model.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {

    fun findByBookingCustomerId(customerId: Long): List<OrderEntity>

    fun findByPurchaseAndBookingCustomerId(purchase: String, customerId: Long): OrderEntity?
}