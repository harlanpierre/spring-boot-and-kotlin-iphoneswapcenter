package br.com.iphoneswapcenter.mapper

import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.model.Book
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface BookMapper {

    @Mapping(source = "id", target = "key")
    fun toBookDTO(book: Book): BookDTO

    @Mapping(source = "id", target = "key")
    fun toDtoList(bookList: ArrayList<Book>): ArrayList<BookDTO>

    @Mapping(source = "key", target = "id")
    fun toBook(bookDTO: BookDTO): Book

    @Mapping(source = "key", target = "id")
    fun toBookList(bookList: ArrayList<BookDTO>): ArrayList<Book>

}