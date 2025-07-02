package com.nauta.api.logistics.customer.service

import com.nauta.api.logistics.customer.model.api.response.RegisteredBookingApiResponse

interface BookingService<E, R> {
    fun registerBooking(registerBookingApiRequest: R): RegisteredBookingApiResponse

    fun processRegisterBookingRequestByCustomer(registerBookingApiRequest: R): E

    fun processNewBookingToRegister(registerBookingApiRequest: R): E

    fun processExistingBookingToUpdate(existingBookingToUpdate: E, registerBookingApiRequest: R): E

    fun processContainersData(existingBookingToUpdate: E, registerBookingApiRequest: R)

    fun processOrdersData(existingBookingToUpdate: E, registerBookingApiRequest: R)
}