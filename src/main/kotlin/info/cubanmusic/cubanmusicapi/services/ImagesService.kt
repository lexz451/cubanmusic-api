package info.cubanmusic.cubanmusicapi.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import info.cubanmusic.cubanmusicapi.repository.ImageRepository
import info.cubanmusic.cubanmusicapi.model.Image
import org.springframework.data.repository.findByIdOrNull

@Service("imagesService")
class ImagesService {
    @Autowired
    lateinit var imagesRepository: ImageRepository;

    fun save(image: Image) = imagesRepository.save(image)

    fun findById(id: Long) = imagesRepository.findByIdOrNull(id)

    fun findAllByIds(ids: List<Long>): MutableList<Image> = imagesRepository.findAllById(ids)
}