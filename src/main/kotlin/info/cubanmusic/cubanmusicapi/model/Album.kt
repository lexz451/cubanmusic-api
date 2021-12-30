package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.springframework.data.jpa.domain.AbstractAuditable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Entity
@Indexed(index = "album_idx")
open class Album  {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    open var id: UUID = UUID.randomUUID()

    @FullTextField
    open var name: String? = null

    @FullTextField
    @Lob
    open var description: String? = null

    @Temporal(TemporalType.DATE)
    open var releaseDate: Date? = null

    open var copyrightYear: Int? = null

    @ManyToMany(mappedBy = "albums")
    open var artists: MutableList<Artist> = mutableListOf()

    @Embedded
    open var imageFile: ImageFile? = null
}