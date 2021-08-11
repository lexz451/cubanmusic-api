package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.Role
import info.cubanmusic.cubanmusicapi.model.User
import info.cubanmusic.cubanmusicapi.security.JwtTokenProvider
import info.cubanmusic.cubanmusicapi.services.UserService
import info.cubanmusic.cubanmusicapi.wrapper.ApiResponse
import info.cubanmusic.cubanmusicapi.wrapper.JWTAuthenticationResponse
import info.cubanmusic.cubanmusicapi.wrapper.SignInRequest
import info.cubanmusic.cubanmusicapi.wrapper.SignUpRequest
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

    @PostMapping("/signin")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    fun signInUser(@RequestBody request: SignInRequest): ResponseEntity<*> {
        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        SecurityContextHolder.getContext().authentication = auth
        val jwt = tokenProvider.generateToken(auth)
        val user = userService.findByEmail(request.email!!)
        return ResponseEntity.ok(
            JWTAuthenticationResponse(
                name = user!!.name!!,
                email = user.email!!,
                role = user.role!!.name,
                accessToken = jwt
            )
        )
    }

    @PostMapping("/signup")
    fun signUpUser(@RequestBody request: SignUpRequest): ResponseEntity<*> {
        if (userService.existsByEmail(request.email)) {
            return ResponseEntity(
                ApiResponse(false, "Email address already in use."),
                HttpStatus.BAD_REQUEST)
        }

        val user = User()
        user.name = request.name
        user.email = request.email
        user.password = passwordEncoder.encode(request.password)
        user.role = Role.CURATOR
        user.enabled = false
        userService.saveUser(user)
        return ResponseEntity(
            ApiResponse(true, "User successfully registered"),
            HttpStatus.OK
        )
    }
}
