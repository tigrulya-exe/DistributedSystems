package ru.nsu.manasyan.osm.model.entity

import javax.persistence.*

@Entity
@Table(name = "Tags")
class TagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    @Column(nullable = false)
    var key: String,
    @Column(nullable = false)
    var value: String,
    @ManyToOne
    @JoinColumn(name = "nodeId", nullable = false)
    var node: NodeEntity
) : Identifiable()