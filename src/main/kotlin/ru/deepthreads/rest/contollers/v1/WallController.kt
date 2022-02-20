package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.*
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.requests.PostCreatingRequest
import ru.deepthreads.rest.models.response.success.*
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.services.WallService

@RestController @RequestMapping("/api/v1/wall")
class WallController {
    @GetMapping
    fun getPosts(
        @RequestHeader("AuthToken") token: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): PostListResponse {
        val posts = WallService.getPosts(
            skip,
            limit,
            AccountService.tokenToUserId(token)
        )
        return PostListResponse(postList = posts)
    }
    @PostMapping("/post")
    fun create(
        @RequestHeader("AuthToken") token: String,
        @RequestBody body: String
    ): PostResponse {
        val data = mapper.readValue(body, PostCreatingRequest::class.java)
        val post = WallService.create(
            AccountService.tokenToUserId(token),
            data.title,
            data.content,
            data.coverResource,
            data.backgroundResource
        )
        return PostResponse(post = post)
    }
    @GetMapping("/{objectId}")
    fun getPost(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): PostResponse {
        val post = WallService.getPost(
            objectId,
            AccountService.tokenToUserId(token)
        )
        return PostResponse(post = post)
    }
    @DeleteMapping("/{objectId}")
    fun deletePost(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        WallService.delete(
            objectId,
            AccountService.getByToken(token).userProfile.objectId
        )
        return EmptyOKResponse()
    }
    @GetMapping("/{objectId}/likes/{likeId}")
    fun getLike(
        @PathVariable("objectId") objectId: String,
        @PathVariable("likeId") likeId: String
    ): LikeResponse {
        val like = WallService.getLike(objectId, likeId)
        return LikeResponse(like = like)
    }
    @GetMapping("/{objectId}/likes")
    fun getLikes(
        @PathVariable("objectId") objectId: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): LikeListResponse {
        val likes = WallService.getLikes(objectId, skip, limit)
        return LikeListResponse(likeList = likes)
    }
    @PostMapping("/{objectId}/like")
    fun like(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): LikeResponse {
        val like = WallService.addLike(
            objectId,
            AccountService.tokenToUserId(token)
        )
        return LikeResponse(like = like)
    }
    @DeleteMapping("/{objectId}/like")
    fun deleteLike(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        WallService.deleteLike(
            objectId,
            AccountService.tokenToUserId(token)
        )
        return EmptyOKResponse()
    }
}