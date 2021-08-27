package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
open class RecordLabel : Organization() {
    @Column(unique = true)
    open var ipiCode: String? = null
    @Column(unique = true)
    open var isniCode: String? = null

}