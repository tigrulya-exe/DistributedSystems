package ru.nsu.manasyan.osm.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Tags")
class TagEntity(
    @Column(nullable = false)
    var key: String,
    @Column(nullable = false)
    var value: String,
) : Identifiable()