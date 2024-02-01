package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.dto.PersonDTO
import br.com.iphoneswapcenter.exception.RequiredObjectIsNullException
import br.com.iphoneswapcenter.model.Person
import br.com.iphoneswapcenter.repository.PersonRepository
import br.com.iphoneswapcenter.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.collections.ArrayList

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    private lateinit var person: Person
    private lateinit var personDTO: PersonDTO
    private lateinit var persisted: Person
    private lateinit var listPerson: ArrayList<Person>

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)

        person = inputObject.mockEntity(1)
        personDTO = inputObject.mockDTO(1)
        listPerson = inputObject.mockEntityList();

        persisted = person.copy()
    }

    @Test
    fun findAll() {
        `when`(repository.findAll()).thenReturn(this.listPerson)

        val people = service.findAll()

        assertNotNull(people)
        assertEquals(14, people.size)

        val personOne = people[1]

        assertNotNull(personOne)
        assertNotNull(personOne.key)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("</person/1>;rel=\"self\""))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)

        val personFour = people[4]

        assertNotNull(personFour)
        assertNotNull(personFour.key)
        assertNotNull(personFour.links)
        assertTrue(personFour.links.toString().contains("</person/4>;rel=\"self\""))
        assertEquals("Address Test4", personFour.address)
        assertEquals("First Name Test4", personFour.firstName)
        assertEquals("Last Name Test4", personFour.lastName)
        assertEquals("Male", personFour.gender)

        val personSeven = people[7]

        assertNotNull(personSeven)
        assertNotNull(personSeven.key)
        assertNotNull(personSeven.links)
        assertTrue(personSeven.links.toString().contains("</person/7>;rel=\"self\""))
        assertEquals("Address Test7", personSeven.address)
        assertEquals("First Name Test7", personSeven.firstName)
        assertEquals("Last Name Test7", personSeven.lastName)
        assertEquals("Female", personSeven.gender)
    }

    @Test
    fun findById() {
        `when`(repository.findById(this.person.id)).thenReturn(Optional.of(this.person))

        val result = service.findById(this.person.id)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun create() {

        `when`(repository.save(this.person)).thenReturn(this.persisted)

        val result = service.create(this.personDTO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) {service.create(null)}

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {

        `when`(repository.findById(this.person.id)).thenReturn(Optional.of(this.person))
        `when`(repository.save(this.person)).thenReturn(this.persisted)

        val result = service.update(this.personDTO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) {service.update(null)}

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        `when`(repository.findById(this.person.id)).thenReturn(Optional.of(this.person))

        service.delete(this.person.id)
    }
}