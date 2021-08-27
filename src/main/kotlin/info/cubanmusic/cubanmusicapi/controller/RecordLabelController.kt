package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.RecordLabelDTO
import info.cubanmusic.cubanmusicapi.model.Phone
import info.cubanmusic.cubanmusicapi.model.RecordLabel
import info.cubanmusic.cubanmusicapi.services.CountryService
import info.cubanmusic.cubanmusicapi.services.LabelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recordlabels")
class RecordLabelController {

    @Autowired
    lateinit var labelService: LabelService
    @Autowired
    lateinit var countryService: CountryService

    @GetMapping("")
    fun findAll(): ResponseEntity<*> {
        val labels = labelService.findAll()
        if (labels.isEmpty()) {
            return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(labels, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        val label = labelService.findById(id)
        label?.let {
            return ResponseEntity(toResponse(it), HttpStatus.OK)
        }
        return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/new")
    fun create(@RequestBody label: RecordLabelDTO): ResponseEntity<*> {
        @Suppress("LocalVariableName")
        var _label = fromRequest(RecordLabel(), label)
        _label = labelService.save(_label)
        return ResponseEntity(_label, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody label: RecordLabelDTO): ResponseEntity<*> {
        @Suppress("LocalVariableName")
        var _label = labelService.findById(id) ?: return ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
        _label = fromRequest(_label, label)
        _label = labelService.save(_label)
        return ResponseEntity(_label, HttpStatus.OK)
    }

    private fun fromRequest(recordLabel: RecordLabel, request: RecordLabelDTO): RecordLabel {
        recordLabel.id = request.id
        recordLabel.ipiCode = request.ipiCode
        recordLabel.isniCode = request.isniCode
        recordLabel.name = request.name ?: ""
        recordLabel.description = request.description ?: ""
        request.country?.let {
            recordLabel.country = countryService.findById(it)
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