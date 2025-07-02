package com.nauta.api.logistics.customer.mock

import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.api.response.RegisteredBookingApiResponse
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import com.nauta.api.logistics.customer.model.mapper.toNewBookingEntity
import com.nauta.api.logistics.customer.util.ObjectMapperUtil

val objectMapperUtil: ObjectMapperUtil = ObjectMapperUtil()

const val token =
    "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDdXN0b21lciBMb2dpc3RpY3MgQVBJIiwic3ViIjoiSldUIFRva2VuIiwidXNlcm5hbWUiOiJ2YWxlQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzUxMzIwNTcwLCJleHAiOjE3NTEzNTA1NzB9.GCUa9ziSyMhm93bngsS6QZ4L6nG0osvpWu1aeIIzwJk"

fun mockValidRequestBody(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-valid.json")

fun mockValidRequestWithOnlyContainer(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-valid-with-container.json")

fun mockValidRequestWithNewContainer(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-new-container.json")

fun mockValidRequestWithExistingContainer(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-existing-container.json")

fun mockValidRequestWithNewOrder(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-new-order.json")

fun mockValidRequestWithExistingOrder(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-existing-order.json")

fun mockValidRequestWithOnlyOrders(): RegisterBookingApiRequest =
    objectMapperUtil.readJsonFromTestResources("request/register-customer-logistic-data-valid-with-order.json")

fun mockResponseOK(): RegisteredBookingApiResponse =
    objectMapperUtil.readJsonFromTestResources("response/created-customer-logistic-data-response-ok.json")

fun mockBookingBaseEntityForTesting(): BookingEntity =
    toNewBookingEntity(CUSTOMER_ID_MOCK, mockValidRequestBody())