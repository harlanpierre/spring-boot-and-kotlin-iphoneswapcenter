package br.com.iphoneswapcenter.integrationtests.dto

import java.util.Date

data class TokenDTO(
    val username: String? = null,
    val authenticated: Boolean? = null,
    val created: Date? = null,
    val expiration: Date? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
