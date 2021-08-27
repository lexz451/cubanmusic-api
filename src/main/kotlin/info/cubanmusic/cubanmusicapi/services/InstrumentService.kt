package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Instrument
import info.cubanmusic.cubanmusicapi.repository.InstrumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("instrumentService")
class InstrumentService {

    @Autowired
    lateinit var instrumentRepository: InstrumentRepository

    fun findAll(): MutableList<Instrument> = instrumentRepository.findAll()

    fun findById(id: Long): Instrument? = instrumentRepository.findByIdOrNull(id)

    fun findAllByIds(ids: List<Long>): MutableList<Instrument> = instrumentRepository.findAllById(ids)

    fun save(instrument: Instrument) = instrumentRepository.save(instrument)
}