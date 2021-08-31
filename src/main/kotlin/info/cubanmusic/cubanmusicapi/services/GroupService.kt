package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service("groupService")
class GroupService {

    @Autowired
    lateinit var groupRepository: GroupRepository

    fun findAll(): MutableList<Group> = groupRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Group> = groupRepository.findAllById(ids)

    fun findById(id: Long): Group? = groupRepository.findByIdOrNull(id)

    fun save(group: Group): Group = groupRepository.save(group)

    fun delete(id: Long) = groupRepository.deleteById(id)
}