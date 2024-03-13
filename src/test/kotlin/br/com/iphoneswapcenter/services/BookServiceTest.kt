package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.exception.RequiredObjectIsNullException
import br.com.iphoneswapcenter.model.Book
import br.com.iphoneswapcenter.repository.BookRepository
import br.com.iphoneswapcenter.unittests.mapper.mocks.MockBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    private lateinit var book: Book
    private lateinit var bookDTO: BookDTO
    private lateinit var persisted: Book
    private lateinit var listBook: ArrayList<Book>

    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)

        book = inputObject.mockEntity(1)
        bookDTO = inputObject.mockDTO(1)
        listBook = inputObject.mockEntityList();

        persisted = book.copy()
    }
/*
    @Test
    fun findAll() {
        Mockito.`when`(repository.findAll()).thenReturn(this.listBook)

        val book = service.findAll()

        assertNotNull(book)
        assertEquals(14, book.size)

        val bookOne = book[1]

        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Author Test1", bookOne.author)
        assertEquals("Title Test1", bookOne.title)
        assertEquals(Date(2024,1,31), bookOne.launchDate)
        assertEquals(65.50, bookOne.price)

        val bookFour = book[4]

        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</api/book/4>;rel=\"self\""))
        assertEquals("Author Test4", bookFour.author)
        assertEquals("Title Test4", bookFour.title)
        assertEquals(Date(2024,1,31), bookFour.launchDate)
        assertEquals(65.50, bookFour.price)

        val bookSeven = book[7]

        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</api/book/7>;rel=\"self\""))
        assertEquals("Author Test7", bookSeven.author)
        assertEquals("Title Test7", bookSeven.title)
        assertEquals(Date(2024,1,31), bookSeven.launchDate)
        assertEquals(65.50, bookSeven.price)
    }

 */

    @Test
    fun findById() {
        Mockito.`when`(repository.findById(this.book.id)).thenReturn(Optional.of(this.book))

        val result = service.findById(this.book.id)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date(2024,1,31), result.launchDate)
        assertEquals(65.50, result.price)
    }

    @Test
    fun create() {

        Mockito.`when`(repository.save(this.book)).thenReturn(this.persisted)

        val result = service.create(this.bookDTO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date(2024,1,31), result.launchDate)
        assertEquals(65.50, result.price)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) {service.create(null)}

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {

        Mockito.`when`(repository.findById(this.book.id)).thenReturn(Optional.of(this.book))
        Mockito.`when`(repository.save(this.book)).thenReturn(this.persisted)

        val result = service.update(this.bookDTO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date(2024,1,31), result.launchDate)
        assertEquals(65.50, result.price)
    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) {service.update(null)}

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        Mockito.`when`(repository.findById(this.book.id)).thenReturn(Optional.of(this.book))

        service.delete(this.book.id)
    }
}