package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface PersonRepository : JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @Cacheable("persons")
    @Query("SELECT * FROM contributor WHERE dtype = 'Person'", nativeQuery = true)
    override fun findAll(): List<Person>
}