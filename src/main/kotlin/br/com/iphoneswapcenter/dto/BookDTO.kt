package br.com.iphoneswapcenter.dto


import org.springframework.hateoas.RepresentationModel
import java.util.*

data class BookDTO (

    var key : Long = 0,
    var author: String = "",
    var launchDate: Date? = null,
    var price: Double = 0.0,
    var title: String = "",

    ) : RepresentationModel<BookDTO>()
