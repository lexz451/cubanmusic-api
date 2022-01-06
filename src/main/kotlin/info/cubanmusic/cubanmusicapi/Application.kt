package info.cubanmusic.cubanmusicapi


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Application : SpringBootServletInitializer() {
	override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder? {
		return builder?.sources(Application::class.java)
	}
}
fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

