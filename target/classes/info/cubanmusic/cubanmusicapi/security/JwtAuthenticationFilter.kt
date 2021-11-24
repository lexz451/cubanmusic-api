package info.cubanmusic.cubanmusicapi.security


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.log

class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val jwt = getJWTFromRequest(request)
            jwt?.let {
                if (it.isNotEmpty() && tokenProvider.validateToken(it)) {
                    val userId = tokenProvider.getUserIdFromJWT(it)
                    val userDetails = userDetailsService.loadUserById(userId)
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            logger.error("Could not set user authentication in security context", e)
        }
        chain.doFilter(request, response)
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader("Authorization")
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
            return bearer.split(" ").last()
        }
        return null
    }
}