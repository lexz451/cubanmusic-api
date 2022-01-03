package info.cubanmusic.cubanmusicapi.audit

import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.LogRepository
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.PostUpdate

@Configurable
class LogListener {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var logRepository: LogRepository

    @PostPersist
    fun onPostPersist(entity: LogEntity) {
        saveLog(entity, Log.LogType.CREATE)
    }

    @PostUpdate
    fun onPostUpdate(entity: LogEntity) {
        saveLog(entity, Log.LogType.UPDATE)
    }

    @PostRemove
    fun onPostRemove(entity: LogEntity) {
        saveLog(entity, Log.LogType.DELETE)
    }

    private fun saveLog(entity: LogEntity, opType: Log.LogType) {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth is AnonymousAuthenticationToken) {
            throw IllegalStateException("Can't save logs for anonymous users.")
        }
        val principal = auth.principal as UserPrincipal
        val currentUser = userRepository.findByIdOrNull(principal.getId())
        val log = Log().apply {
            entityId = entity.entityId()
            entityName = entity.entityName()
            entityType = entity.entityType()
            user = currentUser
            type = opType
        }
        logRepository.save(log)
    }
}