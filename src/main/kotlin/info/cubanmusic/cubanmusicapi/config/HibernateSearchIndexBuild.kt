package info.cubanmusic.cubanmusicapi.config

import info.cubanmusic.cubanmusicapi.controller.VenueController
import org.hibernate.search.mapper.orm.Search
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Configuration
class HibernateSearchIndexBuild : ApplicationListener<ApplicationReadyEvent> {

    private val logger = LoggerFactory.getLogger(HibernateSearchIndexBuild::class.java)

    @Autowired
    private lateinit var em: EntityManager

    @Transactional()
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        logger.info("Started Initializing Indexes")
        val session = Search.session(em)
        val indexer = session.massIndexer().idFetchSize(150).batchSizeToLoadObjects(25)
            .threadsToLoadObjects(12)
        try {
            indexer.startAndWait()
        } catch (e: InterruptedException) {
            logger.warn("Failed to load data from database")
            Thread.currentThread().interrupt()
        }
        logger.info("Completed Indexing")
    }
}