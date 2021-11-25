package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Album
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

@Suppress("FunctionName")
interface AlbumRepository : JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {

    @Cacheable("albums")
    @Query("SELECT * FROM albums", nativeQuery = true)
    override fun findAll(): List<Album>

    @CacheEvict("albums", allEntries = true)
    override fun <S : Album?> save(entity: S): S

    @CacheEvict("albums", allEntries = true)
    override fun deleteById(id: Long)
}