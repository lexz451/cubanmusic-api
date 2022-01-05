package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.*
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
@RequestMapping("/api/v1/groups")
class GroupController {

    private val logger = LoggerFactory.getLogger(GroupController::class.java)

    @Autowired
    private lateinit var groupRepository: GroupRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val groups = groupRepository.findAll().map {
            mapper.map(it, GroupDto::class.java)
        }
        return ResponseEntity.ok(groups)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable id: String): ResponseEntity<Any> {
        val group = groupRepository.findByIdOrNull(UUID.fromString(id))
            ?: return ResponseEntity.notFound().build()
        val response = mapper.map(group, GroupDto::class.java)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/new")
    fun create(@RequestBody groupDto: GroupDto): ResponseEntity<Any> {
        var group = mapper.map(groupDto, Group::class.java)
        group = groupRepository.save(group)
        auditService.logEvent(group, Log.LogType.CREATE)
        return ResponseEntity.ok(group.id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody groupDto: GroupDto): ResponseEntity<Any> {
        var group = mapper.map(groupDto, Group::class.java)
        logger.info(groupDto.albumsIds.map { it.toString() }.toString())
        logger.info(group.albums.map { it.id }.toString())
        group = groupRepository.save(group)
        auditService.logEvent(group, Log.LogType.UPDATE)
        return ResponseEntity.ok(group.id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        val group = groupRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        groupRepository.delete(group)
        auditService.logEvent(group, Log.LogType.DELETE)
        return ResponseEntity.ok().build()
    }

}