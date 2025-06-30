package com.nauta.api.logistics.customer.model.constants

object SecurityConstants {
    const val JWT_SECRET_KEY: String = "JWT_SECRET"
    const val JWT_SECRET_DEFAULT_VALUE: String = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4"
    const val JWT_HEADER: String = "Authorization"
    const val JWT_ISSUER = "Customer Logistics API"
    const val JWT_SUBJECT = "JWT Token"
    const val JWT_EXPIRATION = 30_000_000

    const val HTTP_CONTENT_TYPE = "application/json;charset=UTF-8"

    const val ALLOWED_ORIGIN = "http://localhost:8080"
    const val ALLOWED_METHODS = "*"
    const val ALLOWED_HEADERS = "*"
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BASIC_AUTHORIZATION_PREFIX = "Basic "

    const val CORS_MAX_AGE = 3600L

    const val REGISTER_ENDPOINT = "/register"
    const val LOGIN_ENDPOINT = "/auth/login"
    const val USER_ENDPOINT = "/user"
    const val ERROR_ENDPOINT = "/error"
    const val INVALID_SESSION_ENDPOINT = "/invalidSession"

    const val ORDERS_ENDPOINT = "/api/orders/**"
    const val CONTAINERS_ENDPOINT = "/api/containers/**"

    const val USER_ROLE = "USER"
    const val ADMIN_ROLE = "ADMIN"
    const val USER_CLAIM = "username"
    const val AUTHORITIES_CLAIM = "authorities"
    const val AUTHORIZATION_ERROR = "authorization-error"
    const val AUTHORIZATION_FAILED_MESSAGE = "Authorization failed"

    const val AUTHENTICATION_ERROR = "authentication-error"
    const val AUTHENTICATION_FAILED_MESSAGE = "Authentication failed"

}