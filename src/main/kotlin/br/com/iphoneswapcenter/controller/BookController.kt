package br.com.iphoneswapcenter.controller

import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.services.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
@Tag(name = "Book", description = "Endpoints for Managing Books")
class BookController {

    @Autowired
    private lateinit var service: BookService

    @GetMapping()
    @Operation(summary = "Finds all Books", description = "Finds all Books", tags = ["Book"],
        responses = [
            ApiResponse(description = "Sucess", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = BookDTO::class)))
            ]),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findAll(): List<BookDTO> {
        return service.findAll()
    }

    @GetMapping(value = ["/{id}"])
    @Operation(summary = "Finds a Book", description = "Finds a Book", tags = ["Book"],
        responses = [
            ApiResponse(description = "Sucess", responseCode = "200", content = [
                Content(schema = Schema(implementation = BookDTO::class))
            ]),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findById(@PathVariable(value="id") id: Long): BookDTO {
        return service.findById(id)
    }
    @PostMapping
    @Operation(summary = "Adds a new Person", description = "Adds a new Person", tags = ["Book"],
        responses = [
            ApiResponse(description = "Sucess", responseCode = "200", content = [
                Content(schema = Schema(implementation = BookDTO::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun create(@RequestBody book: BookDTO): BookDTO {
        return service.create(book)
    }

    @PutMapping
    @Operation(summary = "Updates a book's information", description = "Updates a book's information", tags = ["Book"],
        responses = [
            ApiResponse(description = "Sucess", responseCode = "200", content = [
                Content(schema = Schema(implementation = BookDTO::class))
            ]),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun update(@RequestBody book: BookDTO): BookDTO {
        return service.update(book)
    }
    @DeleteMapping(value = ["/{id}"])
    @Operation(summary = "Deletes a book", description = "Deletes a book", tags = ["Book"],
        responses = [
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun delete(@PathVariable(value="id") id: Long) : ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}