package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ArtistRepository : JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
}