package ru.nsu.manasyan.osm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.nsu.manasyan.osm.model.entity.TagEntity

@Repository
interface TagRepository : JpaRepository<TagEntity, Long>