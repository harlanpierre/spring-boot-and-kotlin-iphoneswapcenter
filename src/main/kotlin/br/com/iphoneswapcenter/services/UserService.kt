package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.controller.BookController
import br.com.iphoneswapcenter.dto.BookDTO
import br.com.iphoneswapcenter.exception.RequiredObjectIsNullException
import br.com.iphoneswapcenter.exception.ResourceNotFoundException
import br.com.iphoneswapcenter.mapper.BookMapper
import br.com.iphoneswapcenter.model.Book
import br.com.iphoneswapcenter.repository.BookRepository
import br.com.iphoneswapcenter.repository.UserRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(@field:Autowired var repository: UserRepository) : UserDetailsService {

    private val logger = Logger.getLogger(UserService::class.java.name)

    private val bookMapper: BookMapper = Mappers.getMapper(BookMapper::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding one User by Username $username")
        val user = repository.findByUsername(username)
        return user ?: throw UsernameNotFoundException("Username $username note found")
    }
}