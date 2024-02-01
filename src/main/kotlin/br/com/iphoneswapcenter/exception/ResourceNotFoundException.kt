package br.com.iphoneswapcenter.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(exception: String?): RuntimeException(exception) {
}