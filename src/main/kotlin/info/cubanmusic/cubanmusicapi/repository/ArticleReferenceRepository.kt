package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.ArticleReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ArticleReferenceRepository : JpaRepository<ArticleReference, Long>, JpaSpecificationExecutor<ArticleReference> {

    @Query("SELECT * FROM bibliographic_reference WHERE artist_id = ?1", nativeQuery = true)
    fun findByArtist_Id(id: Long): List<ArticleReference>

}