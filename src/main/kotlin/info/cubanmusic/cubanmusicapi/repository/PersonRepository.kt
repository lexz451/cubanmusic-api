package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface PersonRepository : JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @Cacheable("persons")
    override fun findAll(): List<Person>

    @CacheEvict("persons", allEntries = true)
    override fun <S : Person?> save(entity: S): S

    @CacheEvict("persons", allEntries = true)
    override fun deleteById(id: Long)
}