package info.cubanmusic.cubanmusicapi.model

import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*
import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Type
import java.time.LocalDate


@Table(name = "images")
@Entity
open class Image {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    open var title: String? = null

    open var author: String? = null

    @Temporal(TemporalType.DATE)
    open var date: Date? = null

    @Lob
    open var description: String? = null

    @Embedded
    open var imageFile: ImageFile? = null

    @ElementCollection
    @CollectionTable(name = "images_tags", joinColumns = [JoinColumn(name = "image_id")])
    @Column(name = "tag")
    open var tags: MutableList<String> = mutableListOf()
}