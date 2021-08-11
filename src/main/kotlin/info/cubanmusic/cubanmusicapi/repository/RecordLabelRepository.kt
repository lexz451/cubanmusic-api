package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.RecordLabel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RecordLabelRepository : JpaRepository<RecordLabel, Long>, JpaSpecificationExecutor<RecordLabel> {
}