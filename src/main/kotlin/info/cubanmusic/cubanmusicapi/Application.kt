package info.cubanmusic.cubanmusicapi

import info.cubanmusic.cubanmusicapi.model.Group
import info.cubanmusic.cubanmusicapi.model.Person
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
