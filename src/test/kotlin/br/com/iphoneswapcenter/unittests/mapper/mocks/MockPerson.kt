package br.com.iphoneswapcenter.unittests.mapper.mocks

import br.com.iphoneswapcenter.dto.PersonDTO
import br.com.iphoneswapcenter.model.Person

class MockPerson {
    fun mockEntity(): Person {
        return mockEntity(0)
    }

    fun mockDTO(): PersonDTO {
        return mockDTO(0)
    }

    fun mockEntityList(): ArrayList<Person> {
        val persons: ArrayList<Person> = ArrayList<Person>()
        for (i in 0..13) {
            persons.add(mockEntity(i))
        }
        return persons
    }

    fun mockDTOList(): ArrayList<PersonDTO> {
        val persons: ArrayList<PersonDTO> = ArrayList()
        for (i in 0..13) {
            persons.add(mockDTO(i))
        }
        return persons
    }

    fun mockEntity(number: Int): Person {
        val person = Person()
        person.address = "Address Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "Male" else "Female"
        person.id = number.toLong()
        person.lastName = "Last Name Test$number"
        return person
    }

    fun mockDTO(number: Int): PersonDTO {
        val person = PersonDTO()
        person.address = "Address Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "Male" else "Female"
        person.key = number.toLong()
        person.lastName = "Last Name Test$number"
        return person
    }
}