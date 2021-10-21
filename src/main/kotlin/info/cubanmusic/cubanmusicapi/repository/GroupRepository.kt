package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface GroupRepository : JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    @Cacheable("groups")
    override fun findAll(): List<Group>

    @CacheEvict("groups", allEntries = true)
    override fun <S : Group?> save(entity: S): S

    @CacheEvict("groups", allEntries = true)
    override fun deleteById(id: Long)
}