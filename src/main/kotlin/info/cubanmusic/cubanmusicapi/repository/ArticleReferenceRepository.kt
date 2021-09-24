package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.ArticleReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ArticleReferenceRepository : JpaRepository<ArticleReference, Long>, JpaSpecificationExecutor<ArticleReference> {

    fun findByArtist_Id(id: Long): List<ArticleReference>

}