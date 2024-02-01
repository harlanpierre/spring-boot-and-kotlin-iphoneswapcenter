package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.controller.BookController
import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.exception.RequiredObjectIsNullException
import br.com.iphoneswapcenter.exception.ResourceNotFoundException
import br.com.iphoneswapcenter.mapper.BookMapper
import br.com.iphoneswapcenter.model.Book
import br.com.iphoneswapcenter.repository.BookRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    private val bookMapper: BookMapper = Mappers.getMapper(BookMapper::class.java)

    fun findAll(): List<BookDTO> {
        logger.info("Finding all book!")

        val books = repository.findAll() as ArrayList<Book>
        val book = bookMapper.toDtoList(books)

        for (book in book) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }

        return book
    }

    fun findById(id: Long): BookDTO {
        logger.info("Finding one book!")

        var book = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val bookDTO = bookMapper.toBookDTO(book)

        val withSelfRel = linkTo(BookController::class.java).slash(bookDTO.key).withSelfRel()
        bookDTO.add(withSelfRel)

        return bookDTO
    }

    fun create(book: BookDTO?): BookDTO {
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with title ${book.title}!")
        var entityBook: Book = bookMapper.toBook(book)
        val bookDTO = bookMapper.toBookDTO(repository.save(entityBook))

        val withSelfRel = linkTo(BookController::class.java).slash(bookDTO.key).withSelfRel()
        bookDTO.add(withSelfRel)

        return bookDTO
    }

    fun update(book: BookDTO?): BookDTO {
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Updating one book with ID ${book.key}!")
        val entityBook = findById(book.key)

        entityBook.author = book.author
        entityBook.title = book.title
        entityBook.price = book.price
        entityBook.launchDate = book.launchDate

        val updateBook = repository.save(bookMapper.toBook(entityBook)) //Converting for Book and updating.
        val bookDTO = bookMapper.toBookDTO(updateBook) //Converting for BookDTO and return for Controller.

        val withSelfRel = linkTo(BookController::class.java).slash(bookDTO.key).withSelfRel()
        bookDTO.add(withSelfRel)

        return bookDTO
    }

    fun delete(id: Long) {
        logger.info("Deleting one book with ID $id!")
        val entityBook = findById(id)


        repository.delete(bookMapper.toBook(entityBook))
    }
}