package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.JobTitleDTO
import info.cubanmusic.cubanmusicapi.model.JobTitle
import info.cubanmusic.cubanmusicapi.repository.JobTitleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/jobtitles")
class JobTitleController {

    @Autowired
    lateinit var jobTitleRepository: JobTitleRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val titles = jobTitleRepository.findAll()
        if (titles.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(titles, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun addJobTitle(@RequestBody req: JobTitleDTO): ResponseEntity<*> {
        var job = JobTitle().apply {
            name = req.name
            description = req.description ?: ""
        }
        job = jobTitleRepository.save(job)
        return ResponseEntity(job.id, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteJobTitle(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        jobTitleRepository.findByIdOrNull(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        jobTitleRepository.deleteById(id)
        return ResponseEntity(HttpStatus.OK)
    }
}