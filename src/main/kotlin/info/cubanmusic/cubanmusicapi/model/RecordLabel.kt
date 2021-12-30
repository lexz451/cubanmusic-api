package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
open class RecordLabel {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var name: String? = null

    @Embedded
    open var phone: Phone? = null

    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    open var website: String = ""

    open var address: String = ""

    @Lob
    open var description: String? = null

    @Column(unique = true)
    open var ipiCode: String? = null
    @Column(unique = true)
    open var isniCode: String? = null
}