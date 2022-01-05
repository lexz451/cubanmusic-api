package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Award
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AwardRepository : JpaRepository<Award, UUID>, JpaSpecificationExecutor<Award> {

    @Cacheable("awards")
    override fun findAll(): MutableList<Award>

    @CacheEvict(
        cacheNames = [
            "awards"
        ],
        allEntries = true
    )
    override fun <S : Award?> save(entity: S): S

}