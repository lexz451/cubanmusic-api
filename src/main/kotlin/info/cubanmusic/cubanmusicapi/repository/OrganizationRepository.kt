package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface OrganizationRepository : JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

    @Cacheable("organizations")
    @Query("SELECT * FROM organizations", nativeQuery = true)
    override fun findAll(): List<Organization>
}