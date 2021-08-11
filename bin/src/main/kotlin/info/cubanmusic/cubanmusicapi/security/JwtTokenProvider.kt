package info.cubanmusic.cubanmusicapi.security


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    @Value("\${security.jwtSecret}")
    private var secret: String = "secret"

    @Value("\${security.jwtExpirationInMs}")
    private var expirationTime: Int = 0

    fun generateToken(auth: Authentication): String {
        val userPrincipal = auth.principal as UserPrincipal
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)
        return Jwts.builder()
            .setSubject(userPrincipal.getId().toString())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    public fun getUserIdFromJWT(token: Str)
}