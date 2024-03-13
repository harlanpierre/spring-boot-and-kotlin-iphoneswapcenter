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
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
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
    fun findAll(@RequestParam(value = "page", defaultValue = "0") page: Int,
                @RequestParam(value = "limit", defaultValue = "12") limit: Int,
                @RequestParam(value = "direction", defaultValue = "asc") direction: String
    ): ResponseEntity<PagedModel<EntityModel<BookDTO>>> {
        val sortDirection: Sort.Direction =
            if("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"))
        return ResponseEntity.ok(service.findAll(pageable))
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