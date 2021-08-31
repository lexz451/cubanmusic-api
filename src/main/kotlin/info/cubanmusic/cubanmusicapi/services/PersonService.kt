package info.cubanmusic.cubanmusicapi.services

import info.cubanmusic.cubanmusicapi.model.Person
import info.cubanmusic.cubanmusicapi.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service("personService")
class PersonService {

    @Autowired
    lateinit var personRepository: PersonRepository

    fun findAll(): MutableList<Person> = personRepository.findAll()

    fun findAllByIds(ids: List<Long>): MutableList<Person> = personRepository.findAllById(ids)

    fun findById(id: Long) = personRepository.findByIdOrNull(id)

    fun save(person: Person) = personRepository.save(person)

    fun delete(id: Long) = personRepository.deleteById(id)
}