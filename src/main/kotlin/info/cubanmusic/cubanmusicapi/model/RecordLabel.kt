package info.cubanmusic.cubanmusicapi.model

import javax.persistence.Column
import javax.persistence.Entity

@Entity
open class RecordLabel : Organization() {
    @Column(unique = true)
    open var ipiCode: String? = null
    @Column(unique = true)
    open var isniCode: String? = null
}