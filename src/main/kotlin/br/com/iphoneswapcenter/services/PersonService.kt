package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.controller.PersonController
import br.com.iphoneswapcenter.dto.PersonDTO
import br.com.iphoneswapcenter.exception.RequiredObjectIsNullException
import br.com.iphoneswapcenter.exception.ResourceNotFoundException
import br.com.iphoneswapcenter.mapper.PersonMapper
import br.com.iphoneswapcenter.model.Person
import br.com.iphoneswapcenter.repository.PersonRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonDTO>

    private val logger = Logger.getLogger(PersonService::class.java.name)

    private val personMapper: PersonMapper = Mappers.getMapper(PersonMapper::class.java)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonDTO>> {
        logger.info("Finding all people!")

        val persons = repository.findAll(pageable)
        val people = persons.map { p -> personMapper.toPersonDTO(p) }
        people.map { p -> linkTo(PersonController::class.java).slash(p.key).withSelfRel() }

        return assembler.toModel(people)
    }

    fun findPersonByFirstName(firstName: String, pageable: Pageable): PagedModel<EntityModel<PersonDTO>> {
        logger.info("Finding all people!")

        val persons = repository.findPersonByFirstName(firstName, pageable)
        val people = persons.map { p -> personMapper.toPersonDTO(p) }
        people.map { p -> linkTo(PersonController::class.java).slash(p.key).withSelfRel() }

        return assembler.toModel(people)
    }

    fun findById(id: Long): PersonDTO {
        logger.info("Finding one person!")

        var person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val personDTO = personMapper.toPersonDTO(person)

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun create(person: PersonDTO?): PersonDTO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.firstName}!")
        var entityPerson: Person = personMapper.toPerson(person)
        val personDTO = personMapper.toPersonDTO(repository.save(entityPerson))

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun update(person: PersonDTO?): PersonDTO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Updating one person with ID ${person.key}!")
        val entityPerson = findById(person.key)

        entityPerson.firstName = person.firstName
        entityPerson.lastName = person.lastName
        entityPerson.address = person.address
        entityPerson.gender = person.gender

        val updatePerson = repository.save(personMapper.toPerson(entityPerson)) //Converting for Person and updating.
        val personDTO = personMapper.toPersonDTO(updatePerson) //Converting for PersonDTO and return for Controller.

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    @Transactional
    fun disable(id: Long): PersonDTO {
        logger.info("Disabling one person with ID $id!")
        repository.disablePerson(id)
        var person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val personDTO = personMapper.toPersonDTO(person)

        val withSelfRel = linkTo(PersonController::class.java).slash(personDTO.key).withSelfRel()
        personDTO.add(withSelfRel)

        return personDTO
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entityPerson = findById(id)


        repository.delete(personMapper.toPerson(entityPerson))
    }
}