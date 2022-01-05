package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Log
import info.cubanmusic.cubanmusicapi.wrapper.*
import info.cubanmusic.cubanmusicapi.model.Role
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.model.UserDto
import info.cubanmusic.cubanmusicapi.security.JwtTokenProvider
import info.cubanmusic.cubanmusicapi.services.AuditService
import info.cubanmusic.cubanmusicapi.services.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    @Autowired
    private lateinit var authManager: AuthenticationManager
    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var mapper: ModelMapper
    @Autowired
    private lateinit var auditService: AuditService

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any> {
        val users = userService.findAll().map { mapper.map(it, UserDto::class.java) }
        return ResponseEntity.ok(users)
    }

    @PostMapping("/signin")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<Any> {
        if (request.email.isNullOrEmpty() || request.password.isNullOrEmpty())
            return ResponseEntity.badRequest().build()
        val user = userService.findByEmail(request.email)
            ?: return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        val authToken = UsernamePasswordAuthenticationToken(request.email, request.password)
        val auth = authManager.authenticate(authToken)
        SecurityContextHolder.getContext().authentication = auth
        val jwt = tokenProvider.generateToken(auth)
        return ResponseEntity.ok(
            SignInResponse(
                name = user.name!!,
                email = user.email!!,
                role = user.role!!.name,
                accessToken = jwt
            )
        )
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<*> {
        if (userService.existsByEmail(request.email))
            return ResponseEntity.badRequest().body(
                ApiResponse(false, "Email address already in use."))
        var user = User().apply {
            name = request.name
            email = request.email
            password = passwordEncoder.encode(request.password)
            role = Role.CURATOR
            enabled = true
        }
        user = userService.save(user)
        auditService.logEvent(user, Log.LogType.CREATE)
        return ResponseEntity.ok(ApiResponse(true, "User successfully registered"))
    }
}
