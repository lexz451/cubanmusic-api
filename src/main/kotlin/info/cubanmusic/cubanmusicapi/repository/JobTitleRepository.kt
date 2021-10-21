package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.JobTitle
import org.hibernate.annotations.Cache
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface JobTitleRepository : JpaRepository<JobTitle, Long>, JpaSpecificationExecutor<JobTitle> {

    @Cacheable("jobTitles")
    @Query("SELECT * FROM job_titles", nativeQuery = true)
    override fun findAll(): List<JobTitle>

    @CacheEvict("jobTitles", allEntries = true)
    override fun <S : JobTitle?> save(entity: S): S

    @CacheEvict("jobTitles", allEntries = true)
    override fun deleteById(id: Long)
}