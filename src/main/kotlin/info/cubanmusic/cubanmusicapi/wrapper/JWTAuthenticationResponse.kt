package info.cubanmusic.cubanmusicapi.wrapper

data class JWTAuthenticationResponse(
    val name: String,
    val email: String,
    val role: String,
    val accessToken: String,
    val tokenType: String = "Bearer"
)
