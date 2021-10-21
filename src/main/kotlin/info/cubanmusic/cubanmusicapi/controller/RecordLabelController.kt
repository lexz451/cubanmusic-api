package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.RecordLabelDTO
import info.cubanmusic.cubanmusicapi.model.Phone
import info.cubanmusic.cubanmusicapi.model.RecordLabel
import info.cubanmusic.cubanmusicapi.repository.CountryRepository
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recordlabels")
class RecordLabelController {

    @Autowired
    lateinit var labelRepository: RecordLabelRepository
    @Autowired
    lateinit var countryRepository: CountryRepository

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val labels = labelRepository.findAll()
        if (labels.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(labels.map { toResponse(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val label = labelRepository.findByIdOrNull(id)
        label?.let {
            return ResponseEntity(toResponse(it), HttpStatus.OK)
        }
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/new")
    fun create(@RequestBody request: RecordLabelDTO): ResponseEntity<*> {
        var label = fromRequest(RecordLabel(), request)
        label = labelRepository.save(label)
        return ResponseEntity(label.id, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: RecordLabelDTO): ResponseEntity<*> {
        var label = labelRepository.findByIdOrNull(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        label = fromRequest(label, request)
        labelRepository.save(label)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<*> {
        labelRepository.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.OK)
        labelRepository.deleteById(id)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    private fun fromRequest(recordLabel: RecordLabel, request: RecordLabelDTO): RecordLabel {
        recordLabel.ipiCode = request.ipiCode
        recordLabel.isniCode = request.isniCode
        recordLabel.name = request.name ?: ""
        recordLabel.description = request.description ?: ""
        request.country?.let {
            recordLabel.country = countryRepository.findByIdOrNull(it)
        }
        recordLabel.address = request.address ?: ""
        recordLabel.email = request.email
        recordLabel.phone = request.phone
        recordLabel.website = request.website ?: ""
        return recordLabel
    }

    private fun toResponse(recordLabel: RecordLabel): RecordLabelDTO {
        return RecordLabelDTO().apply {
            id = recordLabel.id
            ipiCode = recordLabel.ipiCode
            isniCode = recordLabel.isniCode
            name = recordLabel.name
            description = recordLabel.description
            country = recordLabel.country?.id
            address = recordLabel.address
            email = recordLabel.email
            phone = recordLabel.phone ?: Phone()
            website = recordLabel.website
        }
    }
}