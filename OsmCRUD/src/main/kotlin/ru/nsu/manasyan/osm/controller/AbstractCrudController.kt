package ru.nsu.manasyan.osm.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.nsu.manasyan.osm.model.entity.Identifiable
import ru.nsu.manasyan.osm.model.mapper.Mapper
import ru.nsu.manasyan.osm.service.AbstractCrudService
import ru.nsu.manasyan.osm.util.PaginationRequestParams


abstract class AbstractCrudController<E : Identifiable, D>(
    protected val service: AbstractCrudService<E>,
    protected val mapper: Mapper<E, D>
) {
    @PostMapping
    open fun create(@RequestBody dto: D) {
        service.add(mapper.toEntity(dto))
    }

    @GetMapping("/{id}")
    open fun get(@PathVariable id: Long): ResponseEntity<*> {
        return ResponseEntity.ok(
            mapper.toDto(service.get(id))
        )
    }

    @GetMapping
    open fun getAll(params: PaginationRequestParams?): ResponseEntity<*> {
        return ResponseEntity.ok(
            mapper.toDtos(service.getAll(params))
        )
    }

    @PutMapping
    open fun update(@RequestBody dto: D) {
        service.update(mapper.toEntity(dto))
    }

    @DeleteMapping("/{id}")
    open fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}