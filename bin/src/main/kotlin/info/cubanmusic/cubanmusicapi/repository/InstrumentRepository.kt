package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.Instrument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface InstrumentRepository : JpaRepository<Instrument, Long>, JpaSpecificationExecutor<Instrument> {
}