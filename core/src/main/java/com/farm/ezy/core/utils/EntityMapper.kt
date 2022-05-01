package com.farm.ezy.core.utils


/**
 * Mapper class to convert one class to another
 */
interface EntityMapper<Entity, DomainEntity> {

    fun mapFromEntity(entity: Entity): DomainEntity

    fun mapToEntity(domainEntity: DomainEntity): Entity
}