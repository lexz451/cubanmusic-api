package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.JobTitle
import info.cubanmusic.cubanmusicapi.model.JobTitleDto
import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.repository.JobTitleRepository
import info.cubanmusic.cubanmusicapi.services.AuditService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/jobtitles")
class JobTitleController {

    @Autowired
    private lateinit var jobTitleRepository: JobTitleRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("")
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<Any> {
        val titles = jobTitleRepository.findAll().map {
            mapper.map(it, JobTitleDto::class.java)
        }
        return ResponseEntity.ok(titles)
    }

    @PostMapping("/new")
    fun create(@RequestBody jobTitleDTO: JobTitleDto): ResponseEntity<Any> {
        var job = mapper.map(jobTitleDTO, JobTitle::class.java)
        job = jobTitleRepository.save(job)
        auditService.logEvent(job, Log.LogType.CREATE)
        return ResponseEntity.ok(job.id)
    }

    @DeleteMapping("/{id}")
    fun deleteJobTitle(@PathVariable id: UUID): ResponseEntity<Any> {
        if (!jobTitleRepository.existsById(id)) return ResponseEntity.notFound().build()
        jobTitleRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }
}