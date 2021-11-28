package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PersonRepository : JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @Cacheable("persons")
    override fun findAll(): List<Person>

    @CacheEvict("persons", allEntries = true)
    override fun <S : Person?> save(entity: S): S

    @CacheEvict("persons", allEntries = true)
    override fun deleteById(id: Long)


    @Query("SELECT\n" +
            "\tc.dtype AS 'type',\n" +
            "\tc.id AS 'id',\n" +
            "\tc.name AS 'name',\n" +
            "\timages.filedata AS 'image',\n" +
            "\timages.filetype as 'image_type',\n" +
            "\tJSON_OBJECT(\n" +
            "\t\t\"job_title\", \n" +
            "\t\tjb.`name`,\n" +
            "\t\t\"birth_date\",\n" +
            "\t\tc.birth_date,\n" +
            "\t\t\"death_date\",\n" +
            "\t\tc.death_date,\n" +
            "\t\t\"birth_place\",\n" +
            "\t\tJSON_OBJECT(\n" +
            "\t\t\t\"city\",\n" +
            "\t\t\ta.city,\n" +
            "\t\t\t\"state\",\n" +
            "\t\t\ta.`state`,\n" +
            "\t\t\t\"country\",\n" +
            "\t\t\tac.`name`\n" +
            "\t\t),\n" +
            "\t\t\"death_place\",\n" +
            "\t\tJSON_OBJECT(\n" +
            "\t\t\t\"city\",\n" +
            "\t\t\tb.city,\n" +
            "\t\t\t\"state\",\n" +
            "\t\t\tb.`state`,\n" +
            "\t\t\t\"country\",\n" +
            "\t\t\tbc.`name`\n" +
            "\t\t)\n" +
            "\t) as 'data'\n" +
            "FROM\n" +
            "\tcontributor c\n" +
            "\tLEFT JOIN images ON c.id = images.artist_id\n" +
            "\tLEFT JOIN job_titles jb ON c.job_title_id = jb.id\n" +
            "\tLEFT JOIN locations a ON c.birth_place_id = a.id\n" +
            "\tLEFT JOIN locations b ON c.death_place_id = b.id\n" +
            "\tLEFT JOIN countries ac ON a.country_id = ac.id\n" +
            "\tLEFT JOIN countries bc ON b.country_id = bc.id\n" +
            "WHERE\n" +
            "\tc.dtype = 'Person' AND c.`name` LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    fun searchByName(@Param("name") name: String?): List<Array<Any?>>

}