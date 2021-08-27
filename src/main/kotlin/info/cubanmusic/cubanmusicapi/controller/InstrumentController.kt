package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.services.InstrumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}