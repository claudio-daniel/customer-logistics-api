package com.nauta.api.logistics.customer.mock

import com.nauta.api.logistics.customer.model.api.response.ContainerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.CustomerContainersApiResponse
import com.nauta.api.logistics.customer.model.entity.ContainerEntity

const val CONTAINER_DATA_BASE_URL = "/api/containers"
const val CONTAINER_ORDES_URL = "orders"

const val CONTAINER_ID_MOCK = 1L

fun mockValidContainersApiResponse(): CustomerContainersApiResponse =
    objectMapperUtil.readJsonFromTestResources("response/valid-containers-api-response.json")

fun mockValidContainerOrdersApiResponse(): ContainerOrdersApiResponse =
    objectMapperUtil.readJsonFromTestResources("response/valid-container-orders-api-response.json")

fun mockValidContainersEntities(): List<ContainerEntity> = mockBookingBaseEntityForTesting().loadAssociations().containers

fun mockValidContainerEntity(): ContainerEntity? = mockValidContainersEntities().first()