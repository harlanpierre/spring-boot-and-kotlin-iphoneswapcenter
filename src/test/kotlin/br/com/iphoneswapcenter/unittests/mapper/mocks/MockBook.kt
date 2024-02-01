package br.com.iphoneswapcenter.unittests.mapper.mocks

import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.model.Book
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockDTO(): BookDTO {
        return mockDTO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockDTOList(): ArrayList<BookDTO> {
        val books: ArrayList<BookDTO> = ArrayList()
        for (i in 0..13) {
            books.add(mockDTO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.author = "Author Test$number"
        book.launchDate = Date(2024, 1, 31)
        book.price = 65.50
        book.id = number.toLong()
        book.title = "Title Test$number"
        return book
    }

    fun mockDTO(number: Int): BookDTO {
        val book = BookDTO()
        book.author = "Author Test$number"
        book.launchDate = Date(2024, 1, 31)
        book.price = 65.50
        book.key = number.toLong()
        book.title = "Title Test$number"
        return book
    }
}