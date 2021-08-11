package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userService")
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun findByEmail(email: String): User? {
        val user = userRepository.findByEmail(email)
        return if (user.isPresent) user.get() else null
    }

    fun saveUser(user: User) = userRepository.save(user)

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}