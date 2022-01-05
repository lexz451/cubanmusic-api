package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Instrument
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface InstrumentRepository : JpaRepository<Instrument, UUID>, JpaSpecificationExecutor<Instrument> {

    @Cacheable("instruments")
    override fun findAll(): MutableList<Instrument>

    @CacheEvict("instruments", allEntries = true)
    override fun <S : Instrument?> save(entity: S): S
}