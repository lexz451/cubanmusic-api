package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.helper.Utils
import info.cubanmusic.cubanmusicapi.model.User
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Cacheable("user", unless = Utils.CACHE_RESULT_NULL)
    @Query("select u from User u where u.id = ?1")
    fun findByIdOrNull(id: Long): User?

    @Query("select u from User u where u.email = ?1")
    fun findByEmail(email: String): User?

    @Cacheable("users", unless = Utils.CACHE_RESULT_EMPTY)
    override fun findAll(): MutableList<User>

    @Caching(
        put = [
            CachePut("user", key = Utils.CACHE_KEY_ID),
        ],
        evict = [
            CacheEvict("users", allEntries = true)
        ]
    )
    override fun <S : User?> save(entity: S): S

    @Caching(
        evict = [
            CacheEvict("user", key = Utils.CACHE_KEY_ID),
            CacheEvict("users", allEntries = true)
        ]
    )
    override fun delete(entity: User)

    fun existsByEmail(email: String): Boolean
}