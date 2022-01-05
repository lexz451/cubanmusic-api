package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.JobTitle
import org.hibernate.annotations.Cache
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface JobTitleRepository : JpaRepository<JobTitle, UUID>, JpaSpecificationExecutor<JobTitle> {

    @Cacheable("jobTitles")
    override fun findAll(): MutableList<JobTitle>

    @CacheEvict("jobTitles", allEntries = true)
    override fun <S : JobTitle?> save(entity: S): S
}