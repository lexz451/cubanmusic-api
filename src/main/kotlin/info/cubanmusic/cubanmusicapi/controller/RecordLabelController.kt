package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.RecordLabel
import info.cubanmusic.cubanmusicapi.model.RecordLabelDto
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/recordlabels")
class RecordLabelController {

    @Autowired
    private lateinit var labelRepository: RecordLabelRepository
    @Autowired
    private lateinit var mapper: ModelMapper

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val labels = labelRepository.findAll().map {
            mapper.map(it, RecordLabelDto::class.java)
        }
        return ResponseEntity.ok(labels)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Any> {
        val label = labelRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        val response = mapper.map(label, RecordLabelDto::class.java)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/new")
    fun create(@RequestBody recordLabelDTO: RecordLabelDto): ResponseEntity<*> {
        var label = mapper.map(recordLabelDTO, RecordLabel::class.java)
        label = labelRepository.save(label)
        return ResponseEntity.ok(label.id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody recordLabelDTO: RecordLabelDto): ResponseEntity<Any> {
        if (!labelRepository.existsById(id)) return ResponseEntity.notFound().build()
        var label = mapper.map(recordLabelDTO, RecordLabel::class.java)
        label = labelRepository.save(label)
        return ResponseEntity.ok(label.id)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        val label = labelRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        labelRepository.delete(label)
        return ResponseEntity.ok().build()
    }
}