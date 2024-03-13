package br.com.iphoneswapcenter.integrationtests.dto.wrappers

import br.com.iphoneswapcenter.integrationtests.dto.PersonDTO
import com.fasterxml.jackson.annotation.JsonProperty

class PersonEmbeddedDTO {

    @JsonProperty("_embedded")
    var persons: List<PersonDTO>? = null
}