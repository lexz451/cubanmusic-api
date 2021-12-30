package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.AlbumDTO
import info.cubanmusic.cubanmusicapi.helper.Utils.formatDate
import info.cubanmusic.cubanmusicapi.model.Album
import info.cubanmusic.cubanmusicapi.model.Venue
import org.hibernate.search.engine.search.common.BooleanOperator
import org.hibernate.search.mapper.orm.Search
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager

@RestController
@RequestMapping("/api/v1/search")
class SearchController {

    private val logger: Logger = LoggerFactory.getLogger(SearchController::class.java)

    @Autowired
    lateinit var em: EntityManager

    @GetMapping("")
    @Transactional(readOnly = true)
    fun search(@RequestParam query: String): ResponseEntity<*> {
        logger.info("Performing full-text search for query: $query")
        val results = Search.session(em).search(
            listOf(Album::class.java, Venue::class.java)
        ).where { f -> f.bool()
            .must(f.simpleQueryString()
                .fields("title", "description", "name")
                .matching(query).defaultOperator(BooleanOperator.OR)) }
            .fetchHits(20)
        val response = results.map(resultMapper)
        return ResponseEntity(response, HttpStatus.OK)
    }

    val resultMapper = { r: Any ->
        when (r) {
            is Album -> {

            }
            else -> r
        }
    }

}