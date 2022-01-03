package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Instrument
import info.cubanmusic.cubanmusicapi.model.InstrumentDto
import info.cubanmusic.cubanmusicapi.repository.InstrumentRepository
import org.modelmapper.ModelMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/instruments")
class InstrumentController {

    @Autowired
    private lateinit var instrumentRepository: InstrumentRepository
    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val instruments = instrumentRepository.findAll().map {
            mapper.map(it, InstrumentDto::class.java)
        }
        return ResponseEntity.ok(instruments)
    }

    @PostMapping("/new")
    fun create(@RequestBody instrumentDTO: InstrumentDto): ResponseEntity<Any> {
        var instrument = mapper.map(instrumentDTO, Instrument::class.java)
        instrument = instrumentRepository.save(instrument)
        return ResponseEntity.ok(instrument.id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        if (!instrumentRepository.existsById(id)) return ResponseEntity.notFound().build()
        instrumentRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }
}