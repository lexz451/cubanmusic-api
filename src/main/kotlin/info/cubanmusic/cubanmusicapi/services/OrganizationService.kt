package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Organization
import info.cubanmusic.cubanmusicapi.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("organizationService")
class OrganizationService {

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    fun findById(id: Long) = organizationRepository.findByIdOrNull(id)

    fun findAll(): MutableList<Organization> = organizationRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Organization> = organizationRepository.findAllById(ids)

    fun save(org: Organization) = organizationRepository.save(org)
}