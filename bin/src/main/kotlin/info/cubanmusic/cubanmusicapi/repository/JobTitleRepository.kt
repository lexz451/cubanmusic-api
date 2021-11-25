package info.cubanmusic.cubanmusicapi.repository;

import info.cubanmusic.cubanmusicapi.model.JobTitle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface JobTitleRepository : JpaRepository<JobTitle, Long>, JpaSpecificationExecutor<JobTitle> {
}