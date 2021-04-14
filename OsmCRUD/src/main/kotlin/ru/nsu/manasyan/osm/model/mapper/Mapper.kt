package ru.nsu.manasyan.osm.model.mapper

import ru.nsu.manasyan.osm.model.entity.Identifiable

interface Mapper<E : Identifiable, D> {
    fun toEntity(dto: D): E

    fun toDto(entity: E): D

    fun toEntities(dtos: Iterable<D>): Iterable<E> = dtos.map(this::toEntity)

    fun toDtos(entities: Iterable<E>): Iterable<D> = entities.map(this::toDto)
}