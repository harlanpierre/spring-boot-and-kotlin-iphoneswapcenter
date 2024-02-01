package br.com.iphoneswapcenter.integrationtests.controller.cors.withjson

import br.com.iphoneswapcenter.integrationtests.TestConfigs
import br.com.iphoneswapcenter.integrationtests.dto.PersonDTO
import br.com.iphoneswapcenter.integrationtests.testcontainers.AbstractIntegrationTest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter

import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson() : AbstractIntegrationTest() {

	private lateinit var specification : RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var person: PersonDTO

	@BeforeAll
	fun setTests() {
		objectMapper = ObjectMapper()
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		person = PersonDTO()
	}

	@Test
	@Order(1)
	fun testCreate() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_LOCALHOST
			)
				.setBasePath("/person")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(RequestLoggingFilter(LogDetail.ALL))
				.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
				.`when`()
			.post()
			.then()
				.statusCode(200)
			.extract()
			.body()
				.asString()

		val createdPerson = objectMapper.readValue(
			content,
			PersonDTO::class.java
		)

		person = createdPerson

		assertNotNull(createdPerson.key)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.key > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasília, DF, Brasil", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(2)
	fun testCreateWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_FOR_TEST
			)
				.setBasePath("/person")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(RequestLoggingFilter(LogDetail.ALL))
				.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
				.`when`()
			.post()
			.then()
				.statusCode(403)
			.extract()
			.body()
				.asString()


		assertEquals("Invalid CORS request", content)
	}

	@Test
	@Order(3)
	fun testFindById() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_LOCALHOST
			)
				.setBasePath("/person")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(RequestLoggingFilter(LogDetail.ALL))
				.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.key)
			.`when`()["{id}"]
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(
			content,
			PersonDTO::class.java
		)
		assertNotNull(createdPerson.key)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.key > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasília, DF, Brasil", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}
	@Test
	@Order(4)
	fun testFindByIdWithWrongOrigin() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGIN,
				TestConfigs.ORIGIN_FOR_TEST
			)
				.setBasePath("/person")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(RequestLoggingFilter(LogDetail.ALL))
				.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.key)
			.`when`()["{id}"]
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	private fun mockPerson() {
		person.firstName = "Nelson"
		person.lastName = "Piquet"
		person.address = "Brasília, DF, Brasil"
		person.gender = "Male"
	}

}