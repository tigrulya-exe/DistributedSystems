package ru.nsu.manasyan.osm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.nsu.manasyan.osm.model.entity.NodeEntity

@Repository
interface NodeRepository : JpaRepository<NodeEntity, Long> {
    @Query(
        value =
        """
            select  n.* 
            from nodes n
            where earth_box(ll_to_earth(?1, ?2), ?3) @> ll_to_earth(n.longitude,n.latitude)
            and earth_distance(
                ll_to_earth(?1, ?2),
                ll_to_earth(n.longitude,n.latitude)
            ) < ?3
            order by earth_distance(
                ll_to_earth(?1, ?2),
                ll_to_earth(n.longitude,n.latitude)
            )
        """,
        nativeQuery = true
    )
    fun getNodesNearby(
        longitude: Double,
        latitude: Double,
        radius: Double
    ): List<NodeEntity>
}