package info.cubanmusic.cubanmusicapi.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    companion object {
        private const val MAX_AGE_SECS = 3600
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:4200",
                "http://localhost:1234",
                "https://admin.cubanmusic.info",
                "https://api.cubanmusic.info",
                "https://cubanmusic.info"
            )
            .allowedHeaders("*")
            .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
            .maxAge(MAX_AGE_SECS.toLong())
    }
}