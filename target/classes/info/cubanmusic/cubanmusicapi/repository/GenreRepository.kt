package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Genre
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface GenreRepository : JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {

    @Cacheable("genres")
    @Query("SELECT * FROM genres", nativeQuery = true)
    override fun findAll(): List<Genre>

    @CacheEvict("genres", allEntries = true)
    override fun <S : Genre?> save(entity: S): S

    @CacheEvict("genres", allEntries = true)
    override fun deleteById(id: Long)
}