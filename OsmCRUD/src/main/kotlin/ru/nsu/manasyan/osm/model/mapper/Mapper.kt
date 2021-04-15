package ru.nsu.manasyan.osm.model.mapper

interface Mapper<E, D> {
    fun map(entity: E): D

    fun mapReversed(entity: D): E

    fun mapIterable(entities: Iterable<E>): Iterable<D> = entities.map(this::map)

    fun mapReversedIterable(entities: Iterable<D>): Iterable<E> = entities.map(this::mapReversed)
}