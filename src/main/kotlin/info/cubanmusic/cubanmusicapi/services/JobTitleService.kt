package info.cubanmusic.cubanmusicapi.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import info.cubanmusic.cubanmusicapi.repository.JobTitleRepository
import info.cubanmusic.cubanmusicapi.model.JobTitle
import org.springframework.data.repository.findByIdOrNull

@Service("jobTitleService")
class JobTitleService {

    @Autowired()
    lateinit var jobTitleRepository: JobTitleRepository

    fun findAll(): List<JobTitle> = jobTitleRepository.findAll()

    fun findById(id: Long) = jobTitleRepository.findByIdOrNull(id)

    fun save(job: JobTitle) = jobTitleRepository.save(job)

    fun delete(id: Long) = jobTitleRepository.deleteById(id)
}