package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.helper.Auditable
import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.LogRepository
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuditService {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var logRepository: LogRepository


    private fun currentUser(): User? {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth.principal as UserPrincipal
        return userRepository.findByIdOrNull(principal.getId())
    }

    fun logEvent(entity: Auditable, event: Log.LogType) {
        val log = Log().apply {
            type = event
            entityName = entity.entityName()
            entityType = entity.entityType()
            entityId = entity.entityId()
            user = currentUser()
        }
        logRepository.save(log)
    }

}