package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
open class Phone {

    @Column(length = 10)
    open var code: String? = null

    open var number: String = ""
}