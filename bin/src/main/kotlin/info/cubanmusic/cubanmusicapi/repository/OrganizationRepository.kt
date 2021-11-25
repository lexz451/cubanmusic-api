package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface OrganizationRepository : JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
}