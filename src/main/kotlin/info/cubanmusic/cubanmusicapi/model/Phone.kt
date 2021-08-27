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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Phone

        if (code != other.code) return false
        if (number != other.number) return false
        return true
    }

    override fun hashCode(): Int {
        var result: Int = code.hashCode()
        result = 31 * result + number.hashCode()
        return result
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(code = $code , number = $number )"
    }
}