package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface OrganizationRepository : JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

    @Cacheable("organizations")
    override fun findAll(): List<Organization>

    @CacheEvict("organizations", allEntries = true)
    override fun <S : Organization?> save(entity: S): S

    @CacheEvict("organizations", allEntries = true)
    override fun deleteById(id: Long)
}