package info.cubanmusic.cubanmusicapi.security

import info.cubanmusic.cubanmusicapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
@Primary
class UserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User not found with email: $email")
        return UserPrincipal.create(user)
    }

    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("User not found with id: $id")
        }
        return UserPrincipal.create(user)
    }
}