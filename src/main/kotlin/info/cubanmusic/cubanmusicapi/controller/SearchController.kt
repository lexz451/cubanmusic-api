package info.cubanmusic.cubanmusicapi.controller

import info.cubanmusic.cubanmusicapi.dto.ResultDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@RestController
@RequestMapping("/api/v1/search")
class SearchController {

    val log: Logger = LoggerFactory.getLogger(SearchController::class.java)

    @PersistenceContext
    lateinit var em: EntityManager

    @PostMapping("")
    fun search(@RequestBody keyword: String?): ResponseEntity<*> {
        if (keyword == null) return ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT)
        val query = em.createNativeQuery(
             "SELECT 'Artist' OriginatingTable, id, name " +
                     "FROM contributor " +
                     "WHERE name LIKE '%$keyword%' AND dtype = 'Person' " +
                     "UNION ALL " +
                     "SELECT 'Album', id, title " +
                     "FROM albums " +
                     "WHERE title LIKE '%$keyword%' " +
                     "UNION ALL " +
                     "SELECT 'Group', id, name " +
                     "FROM contributor " +
                     "WHERE name LIKE '%$keyword%' AND dtype = 'Group' " +
                     "UNION ALL " +
                     "SELECT 'RecordLabel', id, name " +
                     "FROM contributor " +
                     "WHERE name LIKE '%$keyword%' AND dtype = 'RecordLabel' " +
                     "UNION ALL " +
                     "SELECT 'Organization', id, name " +
                     "FROM contributor " +
                     "WHERE name LIKE '%$keyword%' AND dtype = 'Organization' " +
                     "UNION ALL " +
                     "SELECT 'Award', id, title " +
                     "FROM awards " +
                     "WHERE title LIKE '%$keyword%' " +
                     "UNION ALL " +
                     "SELECT 'Venue', id, name " +
                     "FROM venues " +
                     "WHERE name LIKE '%$keyword%'")
        return ResponseEntity(query.resultList, HttpStatus.OK)
    }


}