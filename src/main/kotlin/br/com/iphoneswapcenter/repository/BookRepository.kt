package br.com.iphoneswapcenter.repository

import br.com.iphoneswapcenter.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long?>