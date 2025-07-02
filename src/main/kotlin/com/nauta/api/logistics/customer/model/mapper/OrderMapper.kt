package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.dto.OrderDTO
import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.entity.OrderEntity
import java.time.ZonedDateTime
import kotlin.collections.map
import kotlin.collections.orEmpty

fun toOrderEntity(orderDTO: OrderDTO): OrderEntity = OrderEntity(
    id = null,
    purchase = orderDTO.purchase,
    createdAt = ZonedDateTime.now(),
    booking = null,
    invoices = orderDTO.invoices.toInvoiceEntities()
)

fun toOrderDTO(orderEntity: OrderEntity): OrderDTO =
    OrderDTO(
        purchase = orderEntity.purchase,
        invoices = orderEntity.invoices.toInvoiceDTOs()
    )


fun toNewOrdersFromRequest(registerBookingApiRequest: RegisterBookingApiRequest): MutableSet<OrderEntity> =
    registerBookingApiRequest.orders
        .orEmpty()
        .map { order -> toOrderEntity(order) }
        .toMutableSet()

fun List<OrderDTO>?.toNewOrderEntities(): MutableList<OrderEntity> = this.orEmpty()
    .map { orderDTO -> toOrderEntity(orderDTO) }
    .toMutableList()

fun List<OrderEntity>?.toNewOrderDTOs(): Set<OrderDTO> {
    return this.orEmpty()
        .map { orderEntity -> toOrderDTO(orderEntity) }
        .toSet()
}

fun MutableList<OrderEntity>.addAllOrderEntities(newOrders: MutableSet<OrderEntity>): MutableList<OrderEntity> {
    this.toMutableSet()
        .forEach { registeredOrder ->
            newOrders
                .filter { newOrder -> newOrder.purchase == registeredOrder.purchase }
                .map { newOrder -> registeredOrder.invoices.addAllInvoiceEntities(newOrder.invoices) }
        }

    this.addAll(newOrders)

    return this
}

