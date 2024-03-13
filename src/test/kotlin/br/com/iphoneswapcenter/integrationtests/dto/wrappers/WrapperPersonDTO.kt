package br.com.iphoneswapcenter.integrationtests.dto.wrappers

import com.fasterxml.jackson.annotation.JsonProperty

class WrapperPersonDTO {

    @JsonProperty("_embedded")
    var embedded: PersonEmbeddedDTO? = null
}