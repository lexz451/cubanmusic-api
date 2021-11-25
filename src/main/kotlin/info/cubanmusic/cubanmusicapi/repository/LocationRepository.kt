package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Location
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface LocationRepository : JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    @Cacheable("locations")
    @Query("SELECT * FROM locations", nativeQuery = true)
    override fun findAll(): List<Location>

    @CacheEvict("locations", allEntries = true)
    override fun <S : Location?> save(entity: S): S

    @CacheEvict("locations", allEntries = true)
    override fun deleteById(id: Long)
}