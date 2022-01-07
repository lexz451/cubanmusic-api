package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface OrganizationRepository : JpaRepository<Organization, UUID>, JpaSpecificationExecutor<Organization> {

    @Cacheable("organizations", unless = Utils.CACHE_RESULT_EMPTY)
    //@EntityGraph("organization")
    override fun findAll(): MutableList<Organization>

    @Cacheable("organization", unless = Utils.CACHE_RESULT_NULL)
    //@EntityGraph("organization")
    @Query("select o from Organization o where o.id = ?1")
    fun findByIdOrNull(id: UUID): Organization?

    @Caching(
        evict = [
            CacheEvict("organizations", allEntries = true)
        ],
        put = [
            CachePut("organization", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Organization?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("organizations", allEntries = true),
            CacheEvict("organization", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Organization)
}