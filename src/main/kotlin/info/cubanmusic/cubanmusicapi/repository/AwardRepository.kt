package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Award
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface AwardRepository : JpaRepository<Award, Long>, JpaSpecificationExecutor<Award> {

    @Cacheable("awards")
    @Query("SELECT * FROM awards", nativeQuery = true)
    override fun findAll(): List<Award>
}