package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Country
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CountryRepository : JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {

    @Query("SELECT * FROM countries", nativeQuery = true)
    override fun findAll(): List<Country>

    @Query("SELECT * FROM countries WHERE id = ?1", nativeQuery = true)
    fun findByIdNative(id: Long): Country?
}