package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

@Suppress("FunctionName")
interface AlbumRepository : JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
}