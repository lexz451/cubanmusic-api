package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Venue
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VenueRepository : JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {

    @Cacheable("venues")
    @Query("SELECT * FROM venues", nativeQuery = true)
    override fun findAll(): List<Venue>

    @Query("SELECT * FROM venues WHERE id = ?1", nativeQuery = true)
    fun findByIdNative(id: Long): Venue?

    @CacheEvict("venues", allEntries = true)
    override fun <S : Venue?> save(entity: S): S

    @CacheEvict("venues", allEntries = true)
    override fun deleteById(id: Long)

    @Query("SELECT\n" +
            "\t'Venue' as 'type',\n" +
            "\tv.id as 'id',\n" +
            "\tv.`name` as 'name',\n" +
            "\tv.image as 'image',\n" +
            "\t'NULL' as 'image_type',\n" +
            "\tv.venue_type as 'data'\n" +
            "FROM\n" +
            "\tvenues v\n" +
            "WHERE\n" +
            "\tv.`name` LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    fun searchByName(@Param("name") name: String): List<Any>
}