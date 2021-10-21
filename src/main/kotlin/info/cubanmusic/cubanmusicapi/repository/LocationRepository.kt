package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Location
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface LocationRepository : JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    @Cacheable("locations")
    @Query("SELECT * FROM locations", nativeQuery = true)
    override fun findAll(): List<Location>
}