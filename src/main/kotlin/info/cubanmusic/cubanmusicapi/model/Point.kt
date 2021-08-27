package info.cubanmusic.cubanmusicapi.model

import org.hibernate.Hibernate
import java.math.BigInteger
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
open class Point {
    @Column(length = 25, name = "lat")
    open var latitude: BigInteger = BigInteger.ZERO
    @Column(length = 25, name = "lng")
    open var longitude: BigInteger = BigInteger.ZERO

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Point

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        return true
    }

    override fun hashCode(): Int {
        var result: Int = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(latitude = $latitude , longitude = $longitude )"
    }
}