package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Group
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface GroupRepository : JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    @Cacheable("groups")
    @Query("SELECT * FROM contributor WHERE dtype = 'Group'", nativeQuery = true)
    override fun findAll(): List<Group>
}