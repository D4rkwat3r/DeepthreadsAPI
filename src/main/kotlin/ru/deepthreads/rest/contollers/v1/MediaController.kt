package ru.deepthreads.rest.contollers.v1

import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.models.response.success.UploadFileResponse
import java.io.File

@RestController
class MediaController {

    @PostMapping("/api/v1/upload-media")
    fun upload(
        @RequestParam("extension") type: String,
        @RequestParam("file") file: MultipartFile
    ): UploadFileResponse {
        val name = Utils.fileName(type)
        val path = "/root/deepthreads/new/media/resources/$type"
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val newFile = File("$path/$name")
        newFile.createNewFile()
        file.transferTo(newFile)
        return UploadFileResponse(
            resourceUrl = "http://deepthreads.ru/dynamic/$type/$name"
        )
    }
    @GetMapping("/dynamic/{type}/{name}")
    fun get(
        @PathVariable("type") type: String,
        @PathVariable("name") name: String
    ): ResponseEntity<FileSystemResource> {
        val file = File("/root/deepthreads/new/media/resources/$type/$name")
        var mType = MediaType.APPLICATION_OCTET_STREAM
        when (type) {
            "png" -> mType = MediaType.IMAGE_PNG
            "jpg" -> mType = MediaType.IMAGE_JPEG
            "jpeg" -> mType = MediaType.IMAGE_JPEG
            "gif" -> mType = MediaType.IMAGE_GIF
            "txt" -> mType = MediaType.TEXT_PLAIN
            "mp4" -> mType = MediaType.parseMediaType("video/mp4")
            "avi" -> mType = MediaType.parseMediaType("video/avi")
        }
        return ResponseEntity
            .ok()
            .contentType(mType)
            .body(FileSystemResource(file))
    }
    @GetMapping("/static/{name}")
    fun getStatic(
        @PathVariable("name") name: String
    ): ResponseEntity<FileSystemResource> {
        val file = File("/root/deepthreads/new/media/static/$name")
        var mType = MediaType.APPLICATION_OCTET_STREAM
        when (file.extension) {
            "png" -> mType = MediaType.IMAGE_PNG
            "jpg" -> mType = MediaType.IMAGE_JPEG
            "jpeg" -> mType = MediaType.IMAGE_JPEG
            "gif" -> mType = MediaType.IMAGE_GIF
            "txt" -> mType = MediaType.TEXT_PLAIN
            "mp4" -> mType = MediaType.parseMediaType("video/mp4")
            "avi" -> mType = MediaType.parseMediaType("video/avi")
        }
        return ResponseEntity
            .ok()
            .contentType(mType)
            .body(FileSystemResource(file))
    }
}