package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AwardDTO
import info.cubanmusic.cubanmusicapi.model.Award
import info.cubanmusic.cubanmusicapi.services.AwardService
import info.cubanmusic.cubanmusicapi.services.CountryService
import info.cubanmusic.cubanmusicapi.services.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/awards")
class AwardController {

    @Autowired
    lateinit var awardService: AwardService
    @Autowired
    lateinit var countryService: CountryService
    @Autowired
    lateinit var organizationService: OrganizationService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val awards = awardService.findAll()
        if (awards.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(awards, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val award = awardService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(award), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: AwardDTO): ResponseEntity<*> {
        var award = fromRequest(Award(), request)
        award = awardService.save(award)
        return ResponseEntity(award, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AwardDTO): ResponseEntity<*> {
        var award = awardService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        award = fromRequest(award, request)
        award = awardService.save(award)
        return ResponseEntity(award, HttpStatus.OK)
    }

    private fun fromRequest(award: Award, request: AwardDTO): Award {
        award.id = request.id
        award.title = request.title
        award.description = request.description
        request.country?.let {
            award.country = countryService.findById(it)
        }
        request.grantedBy?.let {
            award.grantedBy = organizationService.findById(it)
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