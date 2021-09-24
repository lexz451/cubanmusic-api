package info.cubanmusic.cubanmusicapi.model


import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.jpa.domain.AbstractAuditable
import javax.persistence.*

@Entity
open class Organization : Contributor() {

    @Lob
    open var description: String? = null

    @Embedded
    open var phone: Phone? = null

    @Column(unique = true, length = 100)
    open var email: String? = null

    @ManyToOne
    @JoinColumn(name = "country_id")
    open var country: Country? = null

    @OneToMany(mappedBy = "grantedBy", cascade = [CascadeType.ALL])
    open var awards: MutableList<Award> = mutableListOf()

    open var website: String = ""

    open var address: String = ""

}