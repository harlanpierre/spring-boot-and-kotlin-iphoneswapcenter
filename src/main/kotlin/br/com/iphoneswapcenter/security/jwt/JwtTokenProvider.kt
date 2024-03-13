package br.com.iphoneswapcenter.security.jwt

import br.com.iphoneswapcenter.dto.TokenDTO
import br.com.iphoneswapcenter.exception.InvalidJwtAuthenticationException
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expire-length: 3600000}")
    private var validityInMillisecond: Long = 3_600_000 //1h

    @Autowired
    private lateinit var userDatailsService: UserDetailsService

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String?>)  : TokenDTO {
        val now = Date()
        val validity = Date(now.time + validityInMillisecond)
        val accessToken = getAccessToken(username, roles, now, validity)
        val refreshToken = getRefreshToken(username, roles, now)
        return TokenDTO(
            username = username,
            authenticated = true,
            accessToken = accessToken,
            refreshToken = refreshToken,
            created = now,
            expiration = validity
        )
    }

    private fun getAccessToken(username: String, roles: List<String?>, now: Date, validity: Date): String {
        val issuerURL : String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    fun refreshToken(refreshToken: String)  : TokenDTO {
        var token: String = ""
        if (refreshToken.contains("Bearer ")) token = refreshToken.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        var decodedJWT: DecodedJWT = verifier.verify(token)
        val username: String = decodedJWT.subject
        val roles: List<String> = decodedJWT.getClaim("roles").asList(String::class.java)
        return createAccessToken(username, roles)
    }

    private fun getRefreshToken(username: String, roles: List<String?>, now: Date): String? {
        val validityRefreshToken = Date(now.time + validityInMillisecond * 3)
        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    fun getAuthentication(token: String): Authentication {
        val decodeJWT: DecodedJWT = decodedToken(token)
        val userDetails: UserDetails = userDatailsService.loadUserByUsername(decodeJWT.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring("Bearer ".length)
        } else null
    }

    fun validateToken(token: String) : Boolean {
        val decodedJWT = decodedToken(token)
        try {
            if (decodedJWT.expiresAt.before(Date())) false
            return true
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token!")
        }
    }
}