package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.exception.CustomerLogisticDataNotFoundException
import com.nauta.api.logistics.customer.exceptionhandling.exception.RegisterCustomerDataException
import com.nauta.api.logistics.customer.mock.CUSTOMER_ID_MOCK
import com.nauta.api.logistics.customer.mock.PURCHASE_ID_MOCK
import com.nauta.api.logistics.customer.mock.mockBadCredentialsException
import com.nauta.api.logistics.customer.mock.mockCustomerDTO
import com.nauta.api.logistics.customer.mock.mockHibernateConstraintViolation
import com.nauta.api.logistics.customer.mock.mockRegisterCustomerLogisticDataException
import com.nauta.api.logistics.customer.mock.mockValidOrderEntity
import com.nauta.api.logistics.customer.mock.mockValidOrdersEntities
import com.nauta.api.logistics.customer.repository.OrderRepository
import com.nauta.api.logistics.customer.service.CustomerService
import com.nauta.api.logistics.customer.service.OrderService
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
class OrderServiceImplTest {

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var customerService: CustomerService

    private lateinit var orderService: OrderService

    @BeforeEach
    fun setup() {
        this.orderService = OrderServiceImpl(orderRepository, customerService)
    }

    @Test
    fun `when there ara data for orders by customer should return response successfully`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { orderRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) } returns mockValidOrdersEntities()

        val containersData = orderService.findOrdersByCustomer()

        assertNotNull(containersData.orders)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { orderRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when repository fails to find orders data should throws exception`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every { orderRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) } throws mockRegisterCustomerLogisticDataException()

        val exception = assertThrows<RegisterCustomerDataException> {
            orderService.findOrdersByCustomer()
        }

        assertEquals("Internal error registering customer logistic data. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { orderRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when find authorized customer fails should not find orders data`() {
        every { customerService.findAuthorizedCustomer() } throws mockBadCredentialsException()

        val exception = assertThrows<BadCredentialsException> {
            orderService.findOrdersByCustomer()
        }

        assertEquals("Invalid credentials.", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 0) { orderRepository.findByBookingCustomerId(CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when there are data for container orders by container id should return response successfully`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            orderRepository.findByPurchaseAndBookingCustomerId(
                PURCHASE_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } returns mockValidOrderEntity()

        val containersData = orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK)

        assertNotNull(containersData)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { orderRepository.findByPurchaseAndBookingCustomerId(PURCHASE_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when there are not data for order containers by purchase id should return not found`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            orderRepository.findByPurchaseAndBookingCustomerId(
                PURCHASE_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } returns null

        val exception = assertThrows<CustomerLogisticDataNotFoundException> {
            orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK)
        }

        assertEquals("Customer logistic data not found. ", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { orderRepository.findByPurchaseAndBookingCustomerId(PURCHASE_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when repository fails to find data for order containers by purchase id should return exception`() {
        every { customerService.findAuthorizedCustomer() } returns mockCustomerDTO()
        every {
            orderRepository.findByPurchaseAndBookingCustomerId(
                PURCHASE_ID_MOCK,
                CUSTOMER_ID_MOCK
            )
        } throws mockHibernateConstraintViolation()

        val exception = assertThrows<ConstraintViolationException> {
            orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK)
        }

        assertEquals("Unique constraint failed", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 1) { orderRepository.findByPurchaseAndBookingCustomerId(PURCHASE_ID_MOCK, CUSTOMER_ID_MOCK) }
    }

    @Test
    fun `when find authorized customer fails to find order containers should not find data`() {
        every { customerService.findAuthorizedCustomer() } throws mockBadCredentialsException()

        val exception = assertThrows<BadCredentialsException> {
            orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK)
        }

        assertEquals("Invalid credentials.", exception.message)

        verify(exactly = 1) { customerService.findAuthorizedCustomer() }
        verify(exactly = 0) { orderRepository.findByPurchaseAndBookingCustomerId(PURCHASE_ID_MOCK, CUSTOMER_ID_MOCK) }
    }
}