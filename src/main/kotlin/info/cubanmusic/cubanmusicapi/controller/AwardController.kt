package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AwardDTO
import info.cubanmusic.cubanmusicapi.model.Award
import info.cubanmusic.cubanmusicapi.repository.AwardRepository
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/awards")
class AwardController {

    @Autowired
    lateinit var awardRepository: AwardRepository
    @Autowired
    lateinit var countryRepository: CountryRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val awards = awardRepository.findAll()
        if (awards.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(awards.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val award = awardRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(award), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: AwardDTO): ResponseEntity<*> {
        var award = fromRequest(Award(), request)
        award = awardRepository.save(award)
        return ResponseEntity(award.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AwardDTO): ResponseEntity<*> {
        var award = awardRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        award = fromRequest(award, request)
        awardRepository.save(award)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        awardRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        awardRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun fromRequest(award: Award, request: AwardDTO): Award {
        award.title = request.title
        award.description = request.description
        request.country?.let {
            award.country = countryRepository.findByIdOrNull(it)
        }
        request.grantedBy?.let {
            award.grantedBy = organizationRepository.findByIdOrNull(it)
        }
        award.categories = request.categories.toMutableSet()
        return award
    }

    private fun toResponse(award: Award): AwardDTO {
        return AwardDTO().apply {
            id  = award.id
            title = award.title ?: ""
            description = award.description
            country = award.country?.id
            grantedBy = award.grantedBy?.id
            categories = award.categories.toMutableList()
        }
    }
}