package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Artist
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ArtistRepository : JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {

    @Cacheable("artists")
    override fun findAll(): MutableList<Artist>

    @CacheEvict(
        cacheNames = [
        "artists"], allEntries = true)
    override fun <S : Artist?> save(entity: S): S
}