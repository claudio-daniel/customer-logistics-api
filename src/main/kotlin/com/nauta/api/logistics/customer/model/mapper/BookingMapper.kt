package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.api.response.RegisteredBookingApiResponse
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import java.time.ZonedDateTime

fun toBookingResponse(bookingEntity: BookingEntity): RegisteredBookingApiResponse =
    RegisteredBookingApiResponse(
        id = bookingEntity.id.toString(),
        booking = bookingEntity.code,
        containers = bookingEntity.containers.toContainerDTOs(),
        orders = bookingEntity.orders.toOrderDTOs()
    )

fun toNewBookingEntity(customerId: Long, registerBookingApiRequest: RegisterBookingApiRequest): BookingEntity =
    BookingEntity(
        id = null,
        code = registerBookingApiRequest.booking,
        customerId = customerId,
        createdAt = ZonedDateTime.now(),
        containers = registerBookingApiRequest.containers.toNewContainerEntities(),
        orders = registerBookingApiRequest.orders.toNewOrderEntities()
    )