package info.cubanmusic.cubanmusicapi.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@NamedEntityGraph(
    name = "log",
    attributeNodes = [
        NamedAttributeNode("user")
    ]
)
@Entity
@Table(name = "log")
open class Log {
    @Id
    @Column(nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID? = UUID.randomUUID()

    @Temporal(TemporalType.TIMESTAMP)
    open var date: Date = Date()

    @Enumerated
    @Column(name = "type")
    open var type: LogType = LogType.CREATE

    open var entityName: String? = null

    open var entityType: String? = null

    open var entityId: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: User? = null

    enum class LogType {
        CREATE,
        UPDATE,
        DELETE
    }
}