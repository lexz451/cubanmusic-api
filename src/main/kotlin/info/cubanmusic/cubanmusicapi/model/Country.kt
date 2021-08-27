package info.cubanmusic.cubanmusicapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "country")
@Entity
open class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    open var name: String = ""

    @Column(length = 2)
    open var iso2Code: String? = null

    @Column(length = 3)
    open var iso3Code: String? = null

    open var phoneCode: String? = null

    open var numericCode: String? = null

    open var emoji: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "country", orphanRemoval = true)
    open var artists: MutableList<Artist> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Country

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 752506755

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , iso2Code = $iso2Code , iso3Code = $iso3Code , phoneCode = $phoneCode , numericCode = $numericCode , emoji = $emoji )"
    }

}