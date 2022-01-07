package info.cubanmusic.cubanmusicapi.controller


import info.cubanmusic.cubanmusicapi.model.*
import info.cubanmusic.cubanmusicapi.repository.*
import info.cubanmusic.cubanmusicapi.wrapper.SearchResponse
import org.hibernate.graph.GraphSemantic
import org.hibernate.search.mapper.orm.Search
import org.hibernate.search.mapper.orm.search.loading.EntityLoadingCacheLookupStrategy
import org.hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/public")
class WebController {

    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var groupRepository: GroupRepository
    @Autowired
    private lateinit var venueRepository: VenueRepository
    @Autowired
    private lateinit var organizationRepository: OrganizationRepository
    @Autowired
    private lateinit var mapper: ModelMapper
    @PersistenceContext
    private lateinit var em: EntityManager

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
        //Album::class.java,
        //RecordLabel::class.java,
        Organization::class.java,
        //Award::class.java,
        Venue::class.java
    )

    @GetMapping("/persons")
    @Transactional(readOnly = true)
    fun findArtists(): ResponseEntity<Any> {
        val artists = personRepository.findAllPublic().map {
            mapper.map(it, PersonWebDto::class.java)
        }
        return ResponseEntity.ok(artists)
    }

    @GetMapping("/persons/{id}")
    @Transactional(readOnly = true)
    fun findArtistById(@PathVariable id: UUID): ResponseEntity<Any> {
        val artist = personRepository.findByIdOrNullPublic(id) ?: return ResponseEntity.notFound().build()
        val res = mapper.map(artist, PersonDetailWebDto::class.java)
        return ResponseEntity.ok(res)
    }

    @GetMapping("/groups")
    @Transactional(readOnly = true)
    fun findGroups(): ResponseEntity<Any> {
        val groups = groupRepository.findAllPublic().map {
            mapper.map(it, GroupWebDto::class.java)
        }
        return ResponseEntity.ok(groups)
    }

    @GetMapping("/venues")
    @Transactional(readOnly = true)
    fun findVenues(): ResponseEntity<Any> {
        val venues = venueRepository.findAllPublic().map {
            mapper.map(it, VenueWebDto::class.java)
        }
        return ResponseEntity.ok(venues)
    }

    @GetMapping("/organizations")
    @Transactional(readOnly = true)
    fun findOrganizations(): ResponseEntity<Any> {
        val venues = organizationRepository.findAllPublic().map {
            mapper.map(it, OrganizationWebDto::class.java)
        }
        return ResponseEntity.ok(venues)
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    fun search(@RequestParam query: String, @RequestParam("result_size") resultSize: Int?): ResponseEntity<*> {
        val results = Search.session(em)
            .search(indexedEntities)
            .where { p -> p.match().fields(*indexedFields).matching(query).fuzzy(2,1) }
            .loading { o ->
                (o as SearchLoadingOptionsStep)
                    //.graph("award", GraphSemantic.FETCH)
                    .graph("person", GraphSemantic.FETCH)
                    .graph("album", GraphSemantic.FETCH)
                    .graph("group", GraphSemantic.FETCH)
                    //.graph("record_label", GraphSemantic.FETCH)
                    .graph("organization", GraphSemantic.FETCH)
                    .cacheLookupStrategy(EntityLoadingCacheLookupStrategy.PERSISTENCE_CONTEXT_THEN_SECOND_LEVEL_CACHE)
            }
            .fetchHits(resultSize ?: 10)
        val response = results.map(resultMapper)
        return ResponseEntity.ok(response)
    }

    private val resultMapper = { r: Any ->
        when (r) {
            //is Album -> SearchResponse(Album::class.qualifiedName, mapper.map(r, AlbumDto::class.java))
            //is Award -> SearchResponse(Award::class.qualifiedName, mapper.map(r, AwardDto::class.java))
            is Group -> SearchResponse(Group::class.qualifiedName, mapper.map(r, GroupWebDto::class.java))
            is Organization -> SearchResponse(Organization::class.qualifiedName, mapper.map(r, OrganizationWebDto::class.java))
            is Person -> SearchResponse(Person::class.qualifiedName, mapper.map(r, PersonWebDto::class.java))
            //is RecordLabel -> SearchResponse(RecordLabel::class.qualifiedName, mapper.map(r, RecordLabelDto::class.java))
            is Venue -> SearchResponse(Venue::class.qualifiedName, mapper.map(r, VenueWebDto::class.java))
            else -> r
        }
    }

}