package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.exception.CustomerLogisticDataNotFoundException
import com.nauta.api.logistics.customer.exceptionhandling.exception.RegisterCustomerDataException
import com.nauta.api.logistics.customer.mock.CONTAINER_ID_MOCK
import com.nauta.api.logistics.customer.mock.CUSTOMER_ID_MOCK
import com.nauta.api.logistics.customer.mock.mockBadCredentialsException
import com.nauta.api.logistics.customer.mock.mockCustomerDTO
import com.nauta.api.logistics.customer.mock.mockHibernateConstraintViolation
import com.nauta.api.logistics.customer.mock.mockRegisterCustomerLogisticDataException
import com.nauta.api.logistics.customer.mock.mockValidContainerEntity
import com.nauta.api.logistics.customer.mock.mockValidContainersEntities
import com.nauta.api.logistics.customer.repository.ContainerRepository
import com.nauta.api.logistics.customer.service.ContainerService
import com.nauta.api.logistics.customer.service.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.BadCredentialsException
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ContainerServiceImplTest {

    @MockK
    private lateinit var customerService: CustomerService

    @MockK
    private lateinit var containerRepository: ContainerRepository

    private lateinit var containerService: ContainerService

    @BeforeEach
    fun setup() {
        this.containerService = ContainerServiceImpl(containerRepository, customerService)
    }

    @Test
    fun `when there ara data for containers by customer should return response successfully`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) } returns mockValidContainersEntities()

        val containersData = containerService.findContainersByCustomer()

        assertNotNull(containersData.containers)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when repository fails to find containers data should throws exception`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) } throws mockRegisterCustomerLogisticDataException()

        val exception = assertThrows<RegisterCustomerDataException> {
            containerService.findContainersByCustomer()
        }

        assertEquals("Internal error registering customer logistic data. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when find authorized customer fails should not find containers data`() {
        every { customerService.findAuthorizedCustomer() } throws mockBadCredentialsException()

        val exception = assertThrows<BadCredentialsException> {
            containerService.findContainersByCustomer()
        }

        assertEquals("Invalid credentials.", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 0) { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when there are data for container orders by container id should return response successfully`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            containerRepository.findByIdAndBookingCustomerId(
                CONTAINER_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } returns mockValidContainerEntity()

        val containersData = containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK)

        assertNotNull(containersData)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { containerRepository.findByIdAndBookingCustomerId(CONTAINER_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when there are not data for container orders by container id should return not found`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            containerRepository.findByIdAndBookingCustomerId(
                CONTAINER_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } returns null

        val exception = assertThrows<CustomerLogisticDataNotFoundException> {
            containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK)
        }

        assertEquals("Customer logistic data not found. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { containerRepository.findByIdAndBookingCustomerId(CONTAINER_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when repository fails to find data for container orders by container id should return exception`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            containerRepository.findByIdAndBookingCustomerId(
                CONTAINER_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } throws mockHibernateConstraintViolation()

        val exception = assertThrows<ConstraintViolationException> {
            containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK)
        }

        assertEquals("Unique constraint failed", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { containerRepository.findByIdAndBookingCustomerId(CONTAINER_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when find authorized customer fails to find container orders should not find data`() {
        every { customerService.findAuthorizedCustomer() } throws mockBadCredentialsException()

        val exception = assertThrows<BadCredentialsException> {
            containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK)
        }

        assertEquals("Invalid credentials.", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 0) { containerRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }
}