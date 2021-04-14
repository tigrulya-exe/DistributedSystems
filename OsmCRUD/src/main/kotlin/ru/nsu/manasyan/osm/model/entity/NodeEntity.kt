package ru.nsu.manasyan.osm.model.entity

import javax.persistence.*

@Entity
@Table(name = "Nodes")
class NodeEntity(
    @Column(nullable = false)
    var user: String,
    @Column(nullable = false)
    var latitude: Double,
    @Column(nullable = false)
    var longitude: Double
) : Identifiable() {
    @OneToMany
    @JoinColumn(name = "nodeId")
    var tags: List<TagEntity> = mutableListOf()
}
