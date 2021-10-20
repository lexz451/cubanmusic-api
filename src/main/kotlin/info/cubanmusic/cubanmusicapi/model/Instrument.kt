package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.AbstractPersistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "instruments", indexes = [
    Index(name = "idx_instrument_id_name_unq", columnList = "id, name", unique = true)
])
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Instrument : AbstractAuditable<User, Long>() {

    open var name: String = ""

    @Lob
    open var description: String = ""
}