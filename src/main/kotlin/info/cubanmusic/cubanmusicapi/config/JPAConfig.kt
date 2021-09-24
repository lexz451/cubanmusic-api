package info.cubanmusic.cubanmusicapi.config

import info.cubanmusic.cubanmusicapi.model.Role
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.reflect.cast

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditingProvider")
class JPAConfig {

    @Autowired
    lateinit var userRepository: UserRepository

    @Bean
    fun customAuditingProvider(): AuditorAware<User> {
        return AuditorAware<User> {
            Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal)
                    .map(UserPrincipal::class::cast)
                    .flatMap { userRepository.findById(it.getId()) }
        }
    }
}