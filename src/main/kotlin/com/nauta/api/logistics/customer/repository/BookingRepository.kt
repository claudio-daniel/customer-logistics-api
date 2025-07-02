package com.nauta.api.logistics.customer.repository

import com.nauta.api.logistics.customer.model.entity.BookingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository : JpaRepository<BookingEntity, Long> {
    fun findByCode(code: String): BookingEntity?
}