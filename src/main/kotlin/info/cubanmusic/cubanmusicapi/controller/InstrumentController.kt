package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.InstrumentDTO
import info.cubanmusic.cubanmusicapi.model.Instrument
import info.cubanmusic.cubanmusicapi.services.InstrumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/instruments")
class InstrumentController {

    @Autowired
    lateinit var instrumentService: InstrumentService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val instruments = instrumentService.findAll()
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
        instrumentService.save(instrument)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteInstrument(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        instrumentService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        instrumentService.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }
}