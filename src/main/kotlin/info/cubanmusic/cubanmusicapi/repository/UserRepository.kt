package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT * FROM users", nativeQuery = true)
    override fun findAll(): List<User>

    @Query("SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    fun findByEmail(email: String): User?


    @Query("select (count(u) > 0) from User u where u.email = ?1")
    fun existsByEmail(email: String): Boolean

}