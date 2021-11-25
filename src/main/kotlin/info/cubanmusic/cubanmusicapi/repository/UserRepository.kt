package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.User
import org.hibernate.annotations.Cache
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Cacheable("users")
    @Query("SELECT * FROM users", nativeQuery = true)
    override fun findAll(): List<User>

    @Cacheable("userByEmail")
    @Query("SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    fun findByEmail(email: String): User?

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    fun existsByEmail(email: String): Boolean

    @CacheEvict("users", "userByEmail", allEntries = true)
    override fun <S : User?> save(entity: S): S

    @CacheEvict("users", "userByEmail", allEntries = true)
    override fun deleteById(id: Long)

    @CacheEvict("users", "userByEmail", allEntries = true)
    override fun delete(entity: User)

}