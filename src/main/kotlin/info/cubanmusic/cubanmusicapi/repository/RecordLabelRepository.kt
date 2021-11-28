package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.RecordLabel
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface RecordLabelRepository : JpaRepository<RecordLabel, Long>, JpaSpecificationExecutor<RecordLabel> {

    @Cacheable("recordLabels")
    override fun findAll(): List<RecordLabel>

    @CacheEvict("recordLabels", allEntries = true)
    override fun <S : RecordLabel?> save(entity: S): S

    @CacheEvict("recordLabels", allEntries = true)
    override fun deleteById(id: Long)

    @Query("SELECT\n" +
            "\tc.dtype AS 'type',\n" +
            "\tc.id AS 'id',\n" +
            "\tc.name AS 'name',\n" +
            "\t'NULL' AS 'image',\n" +
            "\t'NULL' as 'image_type',\n" +
            "\t'NULL' as 'data'\n" +
            "FROM\n" +
            "\tcontributor c\n" +
            "WHERE\n" +
            "\tc.dtype = 'RecordLabel' AND c.`name` LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    fun searchByName(@Param("name") name: String): List<Any>
}