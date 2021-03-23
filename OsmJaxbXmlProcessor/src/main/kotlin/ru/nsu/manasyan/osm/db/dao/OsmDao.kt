package ru.nsu.manasyan.osm.db.dao

interface OsmDao<E> : AutoCloseable {
    fun save(entity: E)

    fun saveAll(entities: Iterable<E>) = entities.forEach { save(it) }
}