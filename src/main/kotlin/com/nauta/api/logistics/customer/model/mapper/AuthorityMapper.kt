package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.request.RegisterCustomerRequest
import com.nauta.api.logistics.customer.model.entity.AuthorityEntity
import com.nauta.api.logistics.customer.model.enums.AuthoritiesDB
import org.springframework.security.core.authority.SimpleGrantedAuthority

fun toAuthorityEntity(registerCustomerRequest: RegisterCustomerRequest): MutableSet<AuthorityEntity> =
    mutableSetOf(
        AuthorityEntity(
            id = null,
            name = AuthoritiesDB.fromString(registerCustomerRequest.role).authorityName,
            customer = null
        )
    )

fun MutableSet<AuthorityEntity>?.toSimpleGrantedAuthorities(): List<SimpleGrantedAuthority> =
    this!!.stream()
        .map { authority -> SimpleGrantedAuthority(authority.name) }
        .toList()