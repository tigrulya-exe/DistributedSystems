package ru.nsu.manasyan.osm.db.dao

// TODO: add TransactionalOsmDao abstract class
interface OsmDao<E> {
    fun save(entity: E)

    fun saveAll(entities: Iterable<E>) = entities.forEach { save(it) }
}