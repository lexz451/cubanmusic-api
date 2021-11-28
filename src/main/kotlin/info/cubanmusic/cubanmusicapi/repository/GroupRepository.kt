package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.model.Organization
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GroupRepository : JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    @Cacheable("groups")
    override fun findAll(): List<Group>

    @CacheEvict("groups", allEntries = true)
    override fun <S : Group?> save(entity: S): S

    @CacheEvict("groups", allEntries = true)
    override fun deleteById(id: Long)

    @Query("SELECT\n" +
            "\tc.dtype AS 'type',\n" +
            "\tc.id AS 'id',\n" +
            "\tc.name AS 'name',\n" +
            "\timages.filedata AS 'image',\n" +
            "\timages.filetype as 'image_type',\n" +
            "\t'NULL' as 'data'\n" +
            "FROM\n" +
            "\tcontributor c\n" +
            "\tLEFT JOIN images ON c.id = images.artist_id\n" +
            "WHERE\n" +
            "\tc.dtype = 'Group' AND c.`name` LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    fun searchByName(@Param("name") name: String): List<Array<Any?>>
}