package br.com.iphoneswapcenter.integrationtests.controller.withjson

import br.com.iphoneswapcenter.integrationtests.TestConfigs
import br.com.iphoneswapcenter.integrationtests.dto.AccountCredentialsDTO
import br.com.iphoneswapcenter.integrationtests.dto.TokenDTO
import br.com.iphoneswapcenter.integrationtests.testcontainers.AbstractIntegrationTest
import io.restassured.RestAssured
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerJsonTest : AbstractIntegrationTest() {

    private lateinit var tokenDTO: TokenDTO

    @BeforeAll
    fun setupTests() {
        tokenDTO = TokenDTO()
    }

    @Test
    @Order(0)
    fun testLogin() {
        val user = AccountCredentialsDTO(
            username = "leandro",
            password = "admin123"
        )

        tokenDTO = RestAssured.given()
            .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenDTO::class.java)

        assertNotNull(tokenDTO.accessToken)
        assertNotNull(tokenDTO.refreshToken)
    }

    @Test
    @Order(1)
    fun testRefreshToken() {

        tokenDTO = RestAssured.given()
            .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParams("username", tokenDTO.username)
                .header(
                        TestConfigs.HEADER_PARAM_AUTHORIZATION,
                        "Bearer ${tokenDTO.refreshToken}"
                )
            .`when`()
                .put("{username}")
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenDTO::class.java)

        assertNotNull(tokenDTO.accessToken)
        assertNotNull(tokenDTO.refreshToken)
    }

}