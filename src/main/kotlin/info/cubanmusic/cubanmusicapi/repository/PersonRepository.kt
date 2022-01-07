package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PersonRepository : JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {

    @Cacheable("persons", unless = Utils.CACHE_RESULT_EMPTY)
    //@EntityGraph("person")
    override fun findAll(): MutableList<Person>

    @Cacheable("persons", unless = Utils.CACHE_RESULT_EMPTY)
    @Query("select p from Person p")
    @EntityGraph("personPublic")
    fun findAllPublic(): MutableList<Person>

    @Cacheable("person", unless = Utils.CACHE_RESULT_NULL)
    @EntityGraph("person")
    @Query("select p from Person p where p.id = ?1")
    fun findByIdOrNull(id: UUID): Person?

    @Cacheable("person", unless = Utils.CACHE_RESULT_NULL)
    @EntityGraph("personPublic")
    @Query("select p from Person p where p.id = ?1")
    fun findByIdOrNullPublic(id: UUID): Person?

    @Caching(
        evict = [
            CacheEvict("persons", allEntries = true),
            CacheEvict("person", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun delete(entity: Person)

    @Caching(
        evict = [
            CacheEvict("persons", allEntries = true)
        ],
        put = [
            CachePut("person", key = Utils.CACHE_KEY_ID)
        ]
    )
    override fun <S : Person?> save(entity: S): S

}