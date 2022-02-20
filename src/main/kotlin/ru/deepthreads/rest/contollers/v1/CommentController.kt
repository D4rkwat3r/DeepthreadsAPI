package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.*
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.requests.CommentingRequest
import ru.deepthreads.rest.models.response.success.*
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.services.CommentService

@RestController @RequestMapping("/api/v1/comments")
class CommentController {
    @PostMapping("/{objectId}")
    fun newComment(
        @RequestBody body: String,
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @RequestParam("type") type: Int
    ): CommentResponse {
        val data = mapper.readValue(body, CommentingRequest::class.java)
        val comment = CommentService.create(
            AccountService.tokenToUserId(token),
            data.content,
            objectId,
            type
        )
        return CommentResponse(comment = comment)
    }
    @GetMapping("/{objectId}/{commentId}")
    fun getComment(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("commentId") commentId: String,
        @RequestParam("type") type: Int
    ): CommentResponse {
        val comment = CommentService.getComment(
            AccountService.tokenToUserId(token),
            objectId,
            type,
            commentId
        )
        return CommentResponse(comment = comment)
    }
    @GetMapping("/{objectId}")
    fun getComments(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @RequestParam("type") type: Int,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): CommentListResponse {
        val comments = CommentService.getComments(
            AccountService.tokenToUserId(token),
            objectId,
            type,
            skip,
            limit
        )
        return CommentListResponse(commentList = comments)
    }
    @PostMapping("/{objectId}/{commentId}/like")
    fun likeComment(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("commentId") commentId: String,
        @RequestParam("type") type: Int
    ): LikeResponse {
        val like = CommentService.likeComment(
            AccountService.tokenToUserId(token),
            objectId,
            type,
            commentId
        )
        return LikeResponse(like = like)
    }
    @DeleteMapping("/{objectId}/{commentId}/like")
    fun unlikeComment(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("commentId") commentId: String,
        @RequestParam("type") type: Int
    ): EmptyOKResponse {
        CommentService.deleteLike(
            AccountService.tokenToUserId(token),
            objectId,
            type,
            commentId
        )
        return EmptyOKResponse()
    }
    @GetMapping("/{objectId}/{commentId}/likes/{likeId}")
    fun getLike(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("commentId") commentId: String,
        @PathVariable("likeId") likeId: String,
        @RequestParam("type") type: Int
    ): LikeResponse {
        val like = CommentService.getLike(
            objectId,
            type,
            commentId,
            likeId
        )
        return LikeResponse(like = like)
    }
    @GetMapping("/{objectId}/{commentId}/likes")
    fun getLikes(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("commentId") commentId: String,
        @RequestParam("type") type: Int,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): LikeListResponse {
        val likes = CommentService.getLikes(
            objectId,
            type,
            commentId,
            skip,
            limit
        )
        return LikeListResponse(likeList = likes)
    }
}