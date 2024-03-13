package br.com.iphoneswapcenter.controller

import br.com.iphoneswapcenter.dto.UploadFileResponseDTO
import br.com.iphoneswapcenter.services.FileStorageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.logging.Logger

@RestController
@RequestMapping("/api/file")
@Tag(name = "File Endpoint")
class FileController {

    private val logger = Logger.getLogger(FileController::class.java.name)

    @Autowired
    private lateinit var fileStorageService: FileStorageService

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile) : UploadFileResponseDTO {
        val fileName = fileStorageService.storeFile(file)
        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/file/uploadFile/")
            .path(fileName)
            .toUriString()
        return UploadFileResponseDTO(fileName, fileDownloadUri, file.contentType!!, file.size)
    }

    @PostMapping("/uploadMultipleFiles")
    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile>) : List<UploadFileResponseDTO> {
        val uploadFileResponseDTOs = files.map { file ->
            uploadFile(file)
        }
        return uploadFileResponseDTOs
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFile(@PathVariable(value = "fileName") fileName: String, request: HttpServletRequest) : ResponseEntity<Resource> {
        val resource = fileStorageService.loadFileAsResource(fileName)
        var contentType = ""
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (e: Exception) {
            logger.info("Could not determine file type!")
        }
        if (contentType.isBlank()) contentType = "application/octet-stream"
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, """attachment; filename="${resource.filename}"""")
            .body(resource)

    }
}
