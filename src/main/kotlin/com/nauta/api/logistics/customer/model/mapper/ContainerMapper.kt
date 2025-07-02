package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.dto.ContainerDTO
import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.entity.ContainerEntity
import java.time.ZonedDateTime
import kotlin.collections.map
import kotlin.collections.orEmpty

fun toNewContainerEntity(containersDTO: ContainerDTO): ContainerEntity = ContainerEntity(
    id = null,
    name = containersDTO.container,
    createdAt = ZonedDateTime.now(),
    booking = null
)

fun toContainerResponse(containerEntity: ContainerEntity): ContainerDTO = ContainerDTO(container = containerEntity.name)

fun toNewContainersFromRequest(registerBookingApiRequest: RegisterBookingApiRequest): MutableSet<ContainerEntity> =
    registerBookingApiRequest.containers.toNewContainerEntitiesSet()

fun List<ContainerDTO>?.toNewContainerEntitiesSet(): MutableSet<ContainerEntity> = this.orEmpty()
    .map { container -> toNewContainerEntity(container) }
    .toMutableSet()

fun List<ContainerDTO>?.toNewContainerEntities(): MutableList<ContainerEntity> = this.orEmpty()
    .map { container -> toNewContainerEntity(container) }
    .toMutableList()

fun List<ContainerEntity>?.toContainerDTOs(): Set<ContainerDTO> {
    return this.orEmpty()
        .map { container -> toContainerResponse(container) }
        .toSet()
}

fun MutableList<ContainerEntity>.addAllContainerEntities(newContainers: MutableSet<ContainerEntity>): MutableList<ContainerEntity> {
    this.addAll(newContainers)
    return this
}