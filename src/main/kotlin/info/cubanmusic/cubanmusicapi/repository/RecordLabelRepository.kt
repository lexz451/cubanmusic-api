package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.RecordLabel
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query


interface RecordLabelRepository : JpaRepository<RecordLabel, Long>, JpaSpecificationExecutor<RecordLabel> {

    @Cacheable("recordLabels")
    override fun findAll(): List<RecordLabel>
}