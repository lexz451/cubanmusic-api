package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Table(name = "countries")
@Entity
open class Country : AbstractAuditable<User, Long>() {

    open var name: String = ""

    @Column(length = 2)
    open var iso2Code: String? = null

    @Column(length = 3)
    open var iso3Code: String? = null

    open var phoneCode: String? = null

    open var numericCode: String? = null

    open var emoji: String? = null
}