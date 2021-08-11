package info.cubanmusic.cubanmusicapi.security


import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.log

@Component
class JwtTokenProvider {

    @Value("\${security.jwtSecret}")
    private var secret: String = "secret"

    @Value("\${security.jwtExpirationInMs}")
    private var expirationTime: String = ""

    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun generateToken(auth: Authentication): String {
        val userPrincipal = auth.principal as UserPrincipal
        val now = Date()
        val expiryDate = Date(now.time + expirationTime.toInt())
        return Jwts.builder()
            .setSubject(userPrincipal.getId().toString())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun getUserIdFromJWT(token: String): Long  {
        val claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
        return claims.subject.toLong()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (e: MalformedJwtException) {
            logger.error("Malformed JWT token")
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty")
        }
        return false
    }
}