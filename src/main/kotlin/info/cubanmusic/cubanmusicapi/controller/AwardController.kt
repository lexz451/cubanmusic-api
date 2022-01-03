package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Award
import info.cubanmusic.cubanmusicapi.model.AwardDto
import info.cubanmusic.cubanmusicapi.repository.AwardRepository
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/awards")
class AwardController {

    @Autowired
    lateinit var awardRepository: AwardRepository
    @Autowired
    lateinit var countryRepository: CountryRepository
    @Autowired
    lateinit var mapper: ModelMapper

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<*> {
        val awards = awardRepository.findAll().map { mapper.map(it, AwardDto::class.java) }
        return ResponseEntity(awards, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: UUID): ResponseEntity<*> {
        val award = awardRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val response = mapper.map(award, AwardDto::class.java)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody awardDTO: AwardDto): ResponseEntity<*> {
        var award = mapper.map(awardDTO, Award::class.java)
        award = awardRepository.save(award)
        return ResponseEntity(award.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody awardDTO: AwardDto): ResponseEntity<*> {
        var award = mapper.map(awardDTO, Award::class.java)
        award = awardRepository.save(award)
        return ResponseEntity(award.id, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*> {
        awardRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

}