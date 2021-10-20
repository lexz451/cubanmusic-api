package info.cubanmusic.cubanmusicapi

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Application
fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
