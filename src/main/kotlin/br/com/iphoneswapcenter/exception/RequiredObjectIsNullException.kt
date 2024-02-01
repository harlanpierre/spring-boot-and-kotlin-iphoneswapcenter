package br.com.iphoneswapcenter.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException : RuntimeException {
    constructor(): super("It is not allowed to persist a null object!")
    constructor(exception: String?): super(exception)

}