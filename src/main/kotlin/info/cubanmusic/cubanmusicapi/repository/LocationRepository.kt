package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Location
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface LocationRepository : JpaRepository<Location, UUID>, JpaSpecificationExecutor<Location> {

    @Cacheable("locations")
    override fun findAll(): MutableList<Location>

    @CacheEvict("locations", allEntries = true)
    override fun <S : Location?> save(entity: S): S
}