package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Award
import info.cubanmusic.cubanmusicapi.repository.AwardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("awardService")
class AwardService {

    @Autowired
    lateinit var awardRepository: AwardRepository

    fun findAll(): MutableList<Award> = awardRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Award> = awardRepository.findAllById(ids)

    fun findById(id: Long) = awardRepository.findByIdOrNull(id)

    fun save(award: Award) = awardRepository.save(award)
}