package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Instrument
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface InstrumentRepository : JpaRepository<Instrument, Long>, JpaSpecificationExecutor<Instrument> {

    @Cacheable("instruments")
    @Query("SELECT * FROM instruments", nativeQuery = true)
    override fun findAll(): List<Instrument>

    @CacheEvict("instruments", allEntries = true)
    override fun <S : Instrument?> save(entity: S): S

    @CacheEvict("instruments", allEntries = true)
    override fun deleteById(id: Long)
}