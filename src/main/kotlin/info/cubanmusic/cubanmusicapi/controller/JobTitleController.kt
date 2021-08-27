package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.services.JobTitleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/jobtitles")
class JobTitleController {

    @Autowired
    lateinit var jobTitleService: JobTitleService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val titles = jobTitleService.findAll()
        if (titles.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(titles, HttpStatus.OK)
    }
}