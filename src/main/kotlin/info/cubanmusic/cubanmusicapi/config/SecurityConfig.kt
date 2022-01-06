package info.cubanmusic.cubanmusicapi.config

import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.repository.UserRepository
import info.cubanmusic.cubanmusicapi.security.JwtAuthenticationEntryPoint
import info.cubanmusic.cubanmusicapi.security.JwtAuthenticationFilter
import info.cubanmusic.cubanmusicapi.security.UserDetailsService
import info.cubanmusic.cubanmusicapi.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.cast


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var unauthorizedHandle: JwtAuthenticationEntryPoint

    @Bean
    fun jwtAuthenticationFilter() = JwtAuthenticationFilter()

    @Throws(Exception::class)
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)
            ?.passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors()
            .and().csrf().disable()
        http.exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandle)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests()
            .antMatchers(
                HttpMethod.GET,
                "/api/v1/public/**",
            )
            .permitAll()
            .antMatchers(
                HttpMethod.POST,
                "/api/v1/auth/signin"
            )
            .permitAll()
            .anyRequest()
            .authenticated()
        http.addFilterBefore(
            jwtAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}