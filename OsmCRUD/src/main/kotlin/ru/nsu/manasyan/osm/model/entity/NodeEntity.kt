package ru.nsu.manasyan.osm.model.entity

import javax.persistence.*

@Entity
@Table(name = "Nodes")
class NodeEntity(
    @Id
    override var id: Long?,
    @Column(name = "[user]", nullable = false)
    var user: String,
    @Column(nullable = false)
    var latitude: Double,
    @Column(nullable = false)
    var longitude: Double
) : Identifiable() {
    @OneToMany(
        mappedBy = "node",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var tags: List<TagEntity> = mutableListOf()
}
