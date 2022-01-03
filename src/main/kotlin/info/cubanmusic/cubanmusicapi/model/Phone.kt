package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
open class Phone {
    @Column(length = 10)
    open var code: String? = null
    open var number: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Phone
        return code == other.code
                && number == other.number
    }

    override fun hashCode(): Int = Objects.hash(code, number);
}