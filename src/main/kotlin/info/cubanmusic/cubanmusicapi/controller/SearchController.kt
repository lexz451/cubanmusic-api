package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.wrapper.SearchResponse
import org.hibernate.search.engine.search.predicate.dsl.SimpleQueryFlag
import org.hibernate.search.mapper.orm.Search
import org.hibernate.search.mapper.orm.search.loading.EntityLoadingCacheLookupStrategy
import org.hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
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
    @Autowired
    lateinit var mapper: ModelMapper

    val indexedFields = arrayOf(
        "name",
        "description",
        "alias",
        "additionalNames",
        "biography",
    )

    val indexedEntities = listOf(
        Person::class.java,
        Group::class.java,
        Album::class.java,
        RecordLabel::class.java,
        Organization::class.java,
        Award::class.java,
        Venue::class.java
    )

    @GetMapping("")
    @Transactional(readOnly = true)
    fun search(@RequestParam query: String): ResponseEntity<*> {
        logger.info("Performing full-text search for query: $query")
        val results = Search.session(em)
            .search(indexedEntities)
            .where { f -> f.bool()
                .must { s -> s.simpleQueryString().field("name").matching("$query~") }
            }
            .loading { o ->
                (o as SearchLoadingOptionsStep).cacheLookupStrategy(
                    EntityLoadingCacheLookupStrategy.PERSISTENCE_CONTEXT_THEN_SECOND_LEVEL_CACHE
                )
            }.fetchHits(20)
        val response = results.map(resultMapper)
        return ResponseEntity.ok(response)
    }

    private val resultMapper = { r: Any ->
        when (r) {
            is Album -> SearchResponse(Album::class.qualifiedName, mapper.map(r, AlbumDto::class.java))
            is Award -> SearchResponse(Award::class.qualifiedName, mapper.map(r, AwardDto::class.java))
            is Group -> SearchResponse(Group::class.qualifiedName, mapper.map(r, GroupDto::class.java))
            is Organization -> SearchResponse(Organization::class.qualifiedName, mapper.map(r, OrganizationDto::class.java))
            is Person -> SearchResponse(Person::class.qualifiedName, mapper.map(r, PersonDto::class.java))
            is RecordLabel -> SearchResponse(RecordLabel::class.qualifiedName, mapper.map(r, RecordLabelDto::class.java))
            is Venue -> SearchResponse(Venue::class.qualifiedName, mapper.map(r, VenueDto::class.java))
            else -> r
        }
    }

}