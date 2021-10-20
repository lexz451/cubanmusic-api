package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.InstrumentDTO
import info.cubanmusic.cubanmusicapi.model.Instrument
import info.cubanmusic.cubanmusicapi.repository.InstrumentRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/instruments")
class InstrumentController {

    @Autowired
    lateinit var instrumentRepository: InstrumentRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val instruments = instrumentRepository.findAll()
        if (instruments.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(instruments, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun addInstrument(@RequestBody req: InstrumentDTO): ResponseEntity<HttpStatus> {
        val instrument = Instrument().apply {
            name = req.name ?: ""
            description = req.description ?: ""
        }
        instrumentRepository.save(instrument)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteInstrument(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        instrumentRepository.findByIdOrNull(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        instrumentRepository.deleteById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}