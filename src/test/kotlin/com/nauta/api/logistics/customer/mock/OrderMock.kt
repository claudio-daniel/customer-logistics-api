package com.nauta.api.logistics.customer.mock

import com.nauta.api.logistics.customer.model.api.response.CustomerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.OrderContainersApiResponse
import com.nauta.api.logistics.customer.model.entity.OrderEntity

const val ORDERS_DATA_BASE_URL = "/api/orders"
const val ORDER_CONTAINERS_URL = "containers"

const val PURCHASE_ID_MOCK = "PO001"

fun mockValidOrdersApiResponse(): CustomerOrdersApiResponse =
    objectMapperUtil.readJsonFromTestResources("response/valid-orders-api-response.json")

fun mockValidOrderContainersApiResponse(): OrderContainersApiResponse =
    objectMapperUtil.readJsonFromTestResources("response/valid-order-containers-api-response.json")

fun mockValidOrdersEntities(): List<OrderEntity> = mockBookingBaseEntityForTesting().loadAssociations().orders

fun mockValidOrderEntity(): OrderEntity = mockValidOrdersEntities().first()