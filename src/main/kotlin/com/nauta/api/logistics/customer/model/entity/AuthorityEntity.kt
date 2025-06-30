package com.nauta.api.logistics.customer.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: CustomerEntity?
)