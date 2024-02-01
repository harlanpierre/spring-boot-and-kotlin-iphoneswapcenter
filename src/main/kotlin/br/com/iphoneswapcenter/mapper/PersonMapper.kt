package br.com.iphoneswapcenter.mapper

import br.com.iphoneswapcenter.dto.PersonDTO
import br.com.iphoneswapcenter.model.Person
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface PersonMapper {

    @Mapping(source = "id", target = "key")
    fun toPersonDTO(person: Person): PersonDTO

    @Mapping(source = "id", target = "key")
    fun toDtoList(personList: ArrayList<Person>): ArrayList<PersonDTO>

    @Mapping(source = "key", target = "id")
    fun toPerson(personDTO: PersonDTO): Person

    @Mapping(source = "key", target = "id")
    fun toPersonList(personList: ArrayList<PersonDTO>): ArrayList<Person>

}