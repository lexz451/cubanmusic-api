package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Image
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ImageRepository : JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {

    @Cacheable("images")
    @Query("SELECT * FROM images", nativeQuery = true)
    override fun findAll(): List<Image>

}