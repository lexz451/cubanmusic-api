package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/logs")
class LogController {

    @Autowired
    private lateinit var logRepository: LogRepository
    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    @Transactional
    fun findAll(): ResponseEntity<Any> {
        val logs = logRepository.findByOrderByDateDesc().map {
            mapper.map(it, LogDto::class.java)
        }
        return ResponseEntity.ok(logs)
    }

}