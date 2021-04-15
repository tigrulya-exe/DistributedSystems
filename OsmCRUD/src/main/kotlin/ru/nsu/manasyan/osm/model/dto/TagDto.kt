package ru.nsu.manasyan.osm.model.dto

data class TagDto(
    val id: Long,
    val key: String,
    val value: String,
    val nodeId: Long
)