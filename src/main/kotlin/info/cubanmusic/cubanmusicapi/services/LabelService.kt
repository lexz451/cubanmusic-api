package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.RecordLabel
import info.cubanmusic.cubanmusicapi.repository.RecordLabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("labelService")
class LabelService {

    @Autowired
    lateinit var labelRepository: RecordLabelRepository

    fun findAll(): MutableList<RecordLabel> = labelRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<RecordLabel> = labelRepository.findAllById(ids)

    fun findById(id: Long): RecordLabel? = labelRepository.findByIdOrNull(id)

    fun save(label: RecordLabel) = labelRepository.save(label)

}