package br.com.iphoneswapcenter.services

import br.com.iphoneswapcenter.config.FileStorageConfig
import br.com.iphoneswapcenter.exception.FileStorageException
import br.com.iphoneswapcenter.exception.MyFileNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService @Autowired constructor(fileStorageConfig: FileStorageConfig) {

    private val fileStorageLocation: Path

    init {
        fileStorageLocation = Paths.get(fileStorageConfig.uploadDir).toAbsolutePath().normalize()
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw FileStorageException("Could not create the directory wher the upload files will be stored", e)
        }
    }

    fun storeFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try {
            if(fileName.contains(".."))
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (e: Exception) {
            throw FileStorageException("Could not store $fileName. Please try again!", e)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) resource
            else throw MyFileNotFoundException("File Not Found $fileName!")
        } catch (e: Exception) {
            throw MyFileNotFoundException("File Not Found $fileName!", e)
        }
    }
}