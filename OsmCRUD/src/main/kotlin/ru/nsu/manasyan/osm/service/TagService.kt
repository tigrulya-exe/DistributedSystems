package ru.nsu.manasyan.osm.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.nsu.manasyan.osm.model.entity.TagEntity

@Service
class TagService(
    repository: JpaRepository<TagEntity, Long>
) : AbstractCrudService<TagEntity>(repository) {
    override val entityName: String = "Tag"
}