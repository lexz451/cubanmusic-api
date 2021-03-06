package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Genre
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GenreRepository : JpaRepository<Genre, UUID>, JpaSpecificationExecutor<Genre> {

    @Cacheable("genres", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<Genre>

    @CacheEvict("genres", allEntries = true)
    override fun <S : Genre?> save(entity: S): S

}