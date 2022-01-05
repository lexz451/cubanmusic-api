package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.model.Person
import info.cubanmusic.cubanmusicapi.model.PersonDto
import info.cubanmusic.cubanmusicapi.repository.*
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/persons")
class PersonController {

    private val logger = LoggerFactory.getLogger(PersonController::class.java)

    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService


    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<*> {
        val persons = personRepository.findAll().map {
            mapper.map(it, PersonDto::class.java)
        }
        return ResponseEntity.ok(persons)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: UUID): ResponseEntity<Any> {
        val person = personRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        val response = mapper.map(person, PersonDto::class.java)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/new")
    fun create(@RequestBody personDto: PersonDto): ResponseEntity<Any> {
        var person = mapper.map(personDto, Person::class.java)
        person = personRepository.save(person)
        auditService.logEvent(person, Log.LogType.CREATE)
        return ResponseEntity.ok(person.id)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody personDto: PersonDto): ResponseEntity<Any> {
        var person = mapper.map(personDto, Person::class.java)
        person = personRepository.save(person)
        auditService.logEvent(person, Log.LogType.UPDATE)
        return ResponseEntity.ok(person.id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        val person = personRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        personRepository.delete(person)
        auditService.logEvent(person, Log.LogType.DELETE)
        return ResponseEntity.ok().build()
    }
}
