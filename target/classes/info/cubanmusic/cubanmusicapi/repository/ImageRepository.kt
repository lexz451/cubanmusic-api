package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Image
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ImageRepository : JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {

    @Cacheable("images")
    @Query("SELECT * FROM images", nativeQuery = true)
    override fun findAll(): List<Image>

    @Cacheable("images")
    @Query("select i from Image i where i.artist.id = ?1")
    fun findByArtistId(id: Long): List<Image>

    @CacheEvict("images", allEntries = true)
    override fun <S : Image?> save(entity: S): S

    @CacheEvict("images", allEntries = true)
    override fun deleteById(id: Long)


    

}