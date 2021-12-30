package info.cubanmusic.cubanmusicapi.config

import info.cubanmusic.cubanmusicapi.helper.Utils.compress
import info.cubanmusic.cubanmusicapi.helper.Utils.decompress
import info.cubanmusic.cubanmusicapi.model.VenueTypes
import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets
import java.util.*

@Configuration
class ModelMapperConfig {

    val stringToUUIDConverter = object : AbstractConverter<String,UUID>() {
        override fun convert(source: String?): UUID {
            if (source == null) return UUID.randomUUID()
            return UUID.fromString(source)
        }
    }

    val stringToVenueType = object : AbstractConverter<String, VenueTypes>() {
        override fun convert(source: String?): VenueTypes? {
            if (source == null) return null
            return VenueTypes.valueOf(source)
        }
    }

    val base64StringToByteArray = object : AbstractConverter<String, ByteArray>() {
        override fun convert(source: String?): ByteArray? {
            if (source == null) return null
            return Base64.getDecoder().decode(
                source.toByteArray(StandardCharsets.UTF_8)
            ).compress()
        }
    }

    val byteArrayToBase64String = object : AbstractConverter<ByteArray, String>() {
        override fun convert(source: ByteArray?): String? {
            if (source == null) return null
            return Base64.getEncoder().encodeToString(
                source.decompress()
            )
        }
    }

    @Bean
    fun modelMapper(): ModelMapper {
        val mapper = ModelMapper()
        mapper.addConverter(stringToUUIDConverter)
        mapper.addConverter(stringToVenueType)
        mapper.addConverter(base64StringToByteArray)
        mapper.addConverter(byteArrayToBase64String)
        return mapper
    }
}