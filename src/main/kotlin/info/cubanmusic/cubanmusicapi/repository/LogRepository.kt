package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Log
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface LogRepository : JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> {

    fun findByOrderByDateAsc(): List<Log>

    @Cacheable("logs")
    fun findByOrderByDateDesc(): List<Log>

    @CacheEvict("logs", allEntries = true)
    override fun <S : Log?> save(entity: S): S

}