package br.com.iphoneswapcenter.dto

import org.springframework.hateoas.RepresentationModel

data class PersonDTO (

    var key: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var enabled: Boolean = true
) : RepresentationModel<PersonDTO>()
