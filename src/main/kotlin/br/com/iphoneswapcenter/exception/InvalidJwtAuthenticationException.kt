package br.com.iphoneswapcenter.exception

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.FORBIDDEN)
class InvalidJwtAuthenticationException(exception: String?) : AuthenticationException(exception)