package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.model.Organization
import info.cubanmusic.cubanmusicapi.model.OrganizationDto
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.repository.OrganizationRepository
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/organizations")
class OrganizationController {
    @Autowired
    lateinit var organizationRepository: OrganizationRepository
    @Autowired
    lateinit var auditService: AuditService

    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val organizations = organizationRepository.findAll().map {
            mapper.map(it, OrganizationDto::class.java)
        }
        return ResponseEntity(organizations, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<*> {
        val organization = organizationRepository.findByIdOrNull(id)
            ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        val response = mapper.map(organization, OrganizationDto::class.java)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun create(@RequestBody organizationDTO: OrganizationDto): ResponseEntity<*> {
        var organization = mapper.map(organizationDTO, Organization::class.java)
        organization = organizationRepository.save(organization)
        auditService.logEvent(organization, Log.LogType.CREATE)
        return ResponseEntity(organization.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody organizationDTO: OrganizationDto):  ResponseEntity<*> {
        var organization = mapper.map(organizationDTO, Organization::class.java)
        organization = organizationRepository.save(organization)
        auditService.logEvent(organization, Log.LogType.UPDATE)
        return ResponseEntity(organization.id, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        val organization = organizationRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        organizationRepository.delete(organization)
        auditService.logEvent(organization, Log.LogType.DELETE)
        return ResponseEntity.ok().build()
    }
}