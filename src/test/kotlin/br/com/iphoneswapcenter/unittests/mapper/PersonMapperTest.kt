package br.com.iphoneswapcenter.unittests.mapper

import br.com.iphoneswapcenter.unittests.mapper.mocks.MockPerson
import br.com.iphoneswapcenter.dto.PersonDTO
import br.com.iphoneswapcenter.mapper.PersonMapper
import br.com.iphoneswapcenter.model.Person
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class PersonMapperTest {

    /*var inputObject: MockPerson? = null
    private var personMapper: PersonMapper = Mappers.getMapper(PersonMapper::class.java)

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
    }

    @Test
    fun parseEntityToDTOTest() {
        val output: PersonDTO = personMapper.toPersonDTO(inputObject!!.mockEntity())
        assertEquals(0, output.id)
        assertEquals("First Name Test0", output.firstName)
        assertEquals("Last Name Test0", output.lastName)
        assertEquals("Address Test0", output.address)
        assertEquals("Male", output.gender)
    }

    @Test
    fun parseEntityListToDTOListTest() {
        val outputList: ArrayList<PersonDTO> = personMapper.toDtoList(inputObject!!.mockEntityList())

        val outputZero: PersonDTO = outputList[0]

        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.firstName)
        assertEquals("Last Name Test0", outputZero.lastName)
        assertEquals("Address Test0", outputZero.address)
        assertEquals("Male", outputZero.gender)

        val outputSeven: PersonDTO = outputList[7]
        assertEquals(7.toLong(), outputSeven.id)
        assertEquals("First Name Test7", outputSeven.firstName)
        assertEquals("Last Name Test7", outputSeven.lastName)
        assertEquals("Address Test7", outputSeven.address)
        assertEquals("Female", outputSeven.gender)

        val outputTwelve: PersonDTO = outputList[12]
        assertEquals(12.toLong(), outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.firstName)
        assertEquals("Last Name Test12", outputTwelve.lastName)
        assertEquals("Address Test12", outputTwelve.address)
        assertEquals("Male", outputTwelve.gender)
    }

    @Test
    fun parseDTOToEntityTest() {

        val output: Person = personMapper.toPerson(inputObject!!.mockDTO())

        assertEquals(0, output.id)
        assertEquals("First Name Test0", output.firstName)
        assertEquals("Last Name Test0", output.lastName)
        assertEquals("Address Test0", output.address)
        assertEquals("Male", output.gender)
    }

    @Test
    fun parserDTOListToEntityListTest() {

        val outputList: ArrayList<Person> = personMapper.toPersonList(inputObject!!.mockDTOList())

        val outputZero: Person = outputList[0]
        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.firstName)
        assertEquals("Last Name Test0", outputZero.lastName)
        assertEquals("Address Test0", outputZero.address)
        assertEquals("Male", outputZero.gender)

        val outputSeven: Person = outputList[7]
        assertEquals(7, outputSeven.id)
        assertEquals("First Name Test7", outputSeven.firstName)
        assertEquals("Last Name Test7", outputSeven.lastName)
        assertEquals("Address Test7", outputSeven.address)
        assertEquals("Female", outputSeven.gender)

        val outputTwelve: Person = outputList[12]
        assertEquals(12, outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.firstName)
        assertEquals("Last Name Test12", outputTwelve.lastName)
        assertEquals("Address Test12", outputTwelve.address)
        assertEquals("Male", outputTwelve.gender)
    }*/
}