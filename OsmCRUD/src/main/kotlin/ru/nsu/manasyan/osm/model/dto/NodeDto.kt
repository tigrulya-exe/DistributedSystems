package ru.nsu.manasyan.osm.model.dto

data class NodeDto(
    val id: Long,
    val user: String,
    val latitude: Double,
    val longitude: Double,
    val tags: List<TagDto>
)