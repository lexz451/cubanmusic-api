package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.Country
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CountryRepository : JpaRepository<Country, UUID>, JpaSpecificationExecutor<Country> {

    @Cacheable("countries", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<Country>

    @CacheEvict("countries", allEntries = true)
    override fun <S : Country?> save(entity: S): S

    fun findByIso2Code(iso2Code: String): Country?

}