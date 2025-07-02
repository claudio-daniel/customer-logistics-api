package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError
import com.nauta.api.logistics.customer.exceptionhandling.exception.RegisterCustomerDataException
import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.api.response.RegisteredBookingApiResponse
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import com.nauta.api.logistics.customer.model.mapper.toNewContainersFromRequest
import com.nauta.api.logistics.customer.model.mapper.toNewBookingEntity
import com.nauta.api.logistics.customer.model.mapper.toNewOrdersFromRequest
import com.nauta.api.logistics.customer.model.mapper.toBookingResponse
import com.nauta.api.logistics.customer.repository.BookingRepository
import com.nauta.api.logistics.customer.service.BookingService
import com.nauta.api.logistics.customer.service.CustomerService
import com.nauta.api.logistics.customer.util.logger
import org.springframework.stereotype.Service

@Service
class BookingServiceImpl(
    private val customerService: CustomerService,
    private val bookingRepository: BookingRepository,
) : BookingService<BookingEntity, RegisterBookingApiRequest> {
    private val log = logger()

    override fun registerBooking(registerBookingApiRequest: RegisterBookingApiRequest): RegisteredBookingApiResponse {
        try {
            return processRegisterBookingRequestByCustomer(registerBookingApiRequest)
                .let { bookingToSave -> bookingRepository.save(bookingToSave.loadAssociations()) }
                .let { processedBookingResult -> toBookingResponse(processedBookingResult) }
        } catch (exception: Exception) {
            log.error("Error registering booking data. ", exception)

            throw RegisterCustomerDataException(
                apiError = ApiError(
                    message = "Customer logistic data not saved. ",
                    code = "ERR-001"
                )
            )
        }
    }

    override fun processRegisterBookingRequestByCustomer(registerBookingApiRequest: RegisterBookingApiRequest): BookingEntity {
        return bookingRepository.findByCode(registerBookingApiRequest.booking)
            ?.let { existingBookingToUpdate ->
                processExistingBookingToUpdate(
                    existingBookingToUpdate,
                    registerBookingApiRequest
                )
            }
            ?: run { processNewBookingToRegister(registerBookingApiRequest) }
    }

    override fun processNewBookingToRegister(registerBookingApiRequest: RegisterBookingApiRequest): BookingEntity {
        log.info("Registering new booking")

        return toNewBookingEntity(
            customerId = customerService.findAuthorizedCustomer().customerId,
            registerBookingApiRequest = registerBookingApiRequest
        )
    }

    override fun processExistingBookingToUpdate(
        existingBookingToUpdate: BookingEntity,
        registerBookingApiRequest: RegisterBookingApiRequest
    ): BookingEntity {
        log.info("Updating registered booking: $registerBookingApiRequest")

        processContainersData(existingBookingToUpdate, registerBookingApiRequest)
        processOrdersData(existingBookingToUpdate, registerBookingApiRequest)

        return existingBookingToUpdate
    }

    override fun processContainersData(
        existingBookingToUpdate: BookingEntity,
        registerBookingApiRequest: RegisterBookingApiRequest
    ) {
        existingBookingToUpdate.refreshContainers(newContainers = toNewContainersFromRequest(registerBookingApiRequest))
    }

    override fun processOrdersData(
        existingBookingToUpdate: BookingEntity,
        registerBookingApiRequest: RegisterBookingApiRequest
    ) {
        existingBookingToUpdate.refreshOrders(ordersToUpdate = toNewOrdersFromRequest(registerBookingApiRequest))
    }
}