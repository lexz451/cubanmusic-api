package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Album
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

@Suppress("FunctionName")
interface AlbumRepository : JpaRepository<Album, UUID>, JpaSpecificationExecutor<Album> {

    @Cacheable("albums")
    override fun findAll(): MutableList<Album>

    @CacheEvict(cacheNames = [
        "albums"
    ], allEntries = true)
    override fun <S : Album?> save(entity: S): S
}