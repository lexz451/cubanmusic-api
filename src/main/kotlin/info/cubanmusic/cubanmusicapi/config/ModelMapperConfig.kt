package info.cubanmusic.cubanmusicapi.config

import info.cubanmusic.cubanmusicapi.helper.Utils.compress
import info.cubanmusic.cubanmusicapi.helper.Utils.decompress
import info.cubanmusic.cubanmusicapi.model.*
import org.modelmapper.*
import org.modelmapper.convention.MatchingStrategies
import org.modelmapper.convention.NamingConventions
import org.modelmapper.spi.NamingConvention
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets
import java.util.*
import javax.persistence.EntityManager

@Configuration
class ModelMapperConfig {

    val locationResponseMapping = object : PropertyMap<Location, LocationResponseDto>() {
        override fun configure() {
            map().id = source.id
            map().name = source.toSingleString()
        }
    }

    @Bean
    fun modelMapper(): ModelMapper {
        val mapper = ModelMapper()
        with(mapper.configuration) {
            isFieldMatchingEnabled = true
            matchingStrategy = MatchingStrategies.STANDARD
        }
        mapper.addMappings(locationResponseMapping)
        return mapper
    }
}