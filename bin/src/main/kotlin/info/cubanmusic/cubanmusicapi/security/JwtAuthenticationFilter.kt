package info.cubanmusic.cubanmusicapi.security


import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter : OncePerRequestFilter() {



    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val jwt = getJWTFromRequest(request)

        } catch (e: Exception) {

        }
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader("Authorization")
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
            return bearer.split(" ").last()
        }
        return null
    }
}