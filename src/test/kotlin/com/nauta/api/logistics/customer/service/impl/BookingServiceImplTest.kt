package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.exception.RegisterCustomerDataException
import com.nauta.api.logistics.customer.mock.mockBadCredentialsException
import com.nauta.api.logistics.customer.mock.mockBookingBaseEntityForTesting
import com.nauta.api.logistics.customer.mock.mockCustomerDTO
import com.nauta.api.logistics.customer.mock.mockHibernateConstraintViolation
import com.nauta.api.logistics.customer.mock.mockValidRequestBody
import com.nauta.api.logistics.customer.mock.mockValidRequestWithExistingContainer
import com.nauta.api.logistics.customer.mock.mockValidRequestWithExistingOrder
import com.nauta.api.logistics.customer.mock.mockValidRequestWithNewContainer
import com.nauta.api.logistics.customer.mock.mockValidRequestWithNewOrder
import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import com.nauta.api.logistics.customer.repository.BookingRepository
import com.nauta.api.logistics.customer.service.BookingService
import com.nauta.api.logistics.customer.service.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class BookingServiceImplTest {

    @MockK
    private lateinit var customerService: CustomerService

    @MockK
    private lateinit var bookingRepository: BookingRepository

    private lateinit var bookingService: BookingService<BookingEntity, RegisterBookingApiRequest>

    @BeforeEach
    fun setup() {
        this.bookingService = BookingServiceImpl(customerService, bookingRepository)
    }

    @Test
    fun `when booking by code exists should process customer logistic data successfully`() {

        val request = mockValidRequestBody()

        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { bookingRepository.findByCode(request.booking) } returns mockBookingBaseEntityForTesting()
        every { bookingRepository.save(any()) } returns mockBookingBaseEntityForTesting()

        val result = bookingService.registerBooking(request)

        assertEquals(request.booking, result.booking)

        verify(exactly = 0) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { bookingRepository.findByCode(request.booking) }
        verify(exactly = 1) { bookingRepository.save(any()) }
    }

    @Test
    fun `when booking by code does not exist should save new customer logistic data successfully`() {

        val request = mockValidRequestBody()

        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { bookingRepository.findByCode(request.booking) } returns null
        every { bookingRepository.save(any()) } returns mockBookingBaseEntityForTesting()

        val result = bookingService.registerBooking(request)

        assertEquals(request.booking, result.booking)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { bookingRepository.findByCode(request.booking) }
        verify(exactly = 1) { bookingRepository.save(any()) }
    }

    @Test
    fun `when service can not save customer data should return exception`() {

        val request = mockValidRequestBody()

        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { bookingRepository.findByCode(request.booking) } returns null
        every { bookingRepository.save(any()) } throws mockHibernateConstraintViolation()

        val exception = assertThrows<RegisterCustomerDataException> {
            bookingService.registerBooking(request)
        }

        assertEquals("Internal error registering customer logistic data. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { bookingRepository.findByCode(request.booking) }
        verify(exactly = 1) { bookingRepository.save(any()) }
    }

    @Test
    fun `when customer authentication fails should not save any customer logistic data`() {

        val request = mockValidRequestBody()

        every { customerService.findAuthorizedCustomer() } throws mockBadCredentialsException()
        every { bookingRepository.findByCode(request.booking) } returns null

        val exception = assertThrows<RegisterCustomerDataException> {
            bookingService.registerBooking(request)
        }

        assertEquals("Internal error registering customer logistic data. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { bookingRepository.findByCode(request.booking) }
        verify(exactly = 0) { bookingRepository.save(any()) }
    }

    @Test
    fun `when receive customer data with unexist container should add new container to booking`() {

        val existingBooking = mockBookingBaseEntityForTesting()

        val existingContainerSizeBeforeProcessRequest = existingBooking.containers.size

        val request = mockValidRequestWithNewContainer()

        bookingService.processContainersData(existingBooking, request)

        val expectedContainersSizeAfterProcessRequest =
            existingContainerSizeBeforeProcessRequest + request.containers!!.size

        assertEquals(
            expectedContainersSizeAfterProcessRequest,
            existingBooking.containers.toSet().size
        )
    }

    @Test
    fun `when receive customer data with existing container should not add any container to booking`() {

        val existingBooking = mockBookingBaseEntityForTesting()

        val existingContainerSizeBeforeProcessRequest = existingBooking.containers.size

        val request = mockValidRequestWithExistingContainer()

        bookingService.processContainersData(existingBooking, request)

        assertEquals(
            existingContainerSizeBeforeProcessRequest,
            existingBooking.containers.toSet().size
        )
    }

    @Test
    fun `when receive customer data with unexist order should add new order to booking`() {

        val existingBooking = mockBookingBaseEntityForTesting()

        val existingOrdersSizeBeforeProcessRequest = existingBooking.orders.size

        val request = mockValidRequestWithNewOrder()

        bookingService.processOrdersData(existingBooking, request)

        val expectedOrdersSizeAfterProcessRequest =
            existingOrdersSizeBeforeProcessRequest + request.orders!!.size

        assertEquals(
            expectedOrdersSizeAfterProcessRequest,
            existingBooking.orders.toSet().size
        )
    }

    @Test
    fun `when receive customer data with existing order should not add new order to booking`() {

        val existingBooking = mockBookingBaseEntityForTesting()

        val existingOrdersSizeBeforeProcessRequest = existingBooking.orders.size

        val request = mockValidRequestWithExistingOrder()

        bookingService.processOrdersData(existingBooking, request)

        assertEquals(
            existingOrdersSizeBeforeProcessRequest,
            existingBooking.orders.toSet().size
        )
    }
}