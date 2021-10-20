package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.OrganizationDTO
import info.cubanmusic.cubanmusicapi.model.Organization
import info.cubanmusic.cubanmusicapi.model.Phone

import info.cubanmusic.cubanmusicapi.repository.OrganizationRepository
import info.cubanmusic.cubanmusicapi.services.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/organizations")
class OrganizationController {
    @Autowired
    lateinit var organizationRepository: OrganizationRepository
    @Autowired
    lateinit var countryService: CountryService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val organizations = organizationRepository.findAll()
        if (organizations.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(organizations.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val org = organizationRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        return ResponseEntity(toResponse(org), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: OrganizationDTO): ResponseEntity<*> {
        val org = fromRequest(Organization(), request)
        organizationRepository.save(org)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: OrganizationDTO):  ResponseEntity<*> {
        var org = organizationRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        org = fromRequest(org, request)
        organizationRepository.save(org)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        organizationRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        organizationRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun fromRequest(org: Organization, request: OrganizationDTO): Organization {
        org.name = request.name ?: ""
        org.description = request.description ?: ""
        request.country?.let {
            org.country = countryService.findById(it)
        }
        org.address = request.address ?: ""
        org.email = request.email
        org.phone = request.phone
        org.website = request.website ?: ""
        return org
    }

    private fun toResponse(org: Organization): OrganizationDTO {
        return OrganizationDTO().apply {
            id = org.id
            name = org.name
            description = org.description
            country = org.country?.id
            address = org.address
            email = org.email
            phone = org.phone ?: Phone()
            website = org.website
        }
    }
}